package com.lovevery.messagemanager.usermessages.presentation.viewmodels

import com.lovevery.messagemanager.shared.data.MessageResponse
import com.lovevery.messagemanager.shared.presentation.MessageModel
import com.lovevery.messagemanager.shared.toMessageModel
import com.lovevery.messagemanager.usermessages.data.UserMessageResponse
import com.lovevery.messagemanager.usermessages.domain.GetAllMessagesByUserNameUseCase
import com.lovevery.messagemanager.usermessages.presentation.uistate.UserMessagesUiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class UserMessagesViewModelTest {
    private lateinit var messageResponseList: List<MessageResponse>
    private lateinit var messageModelList: List<MessageModel>
    private lateinit var userMessageResponse: UserMessageResponse
    private lateinit var username: String
    private lateinit var userMessagesViewModel: UserMessagesViewModel
    private lateinit var getAllMessagesByUserNameUseCase: GetAllMessagesByUserNameUseCase
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        getAllMessagesByUserNameUseCase = mockk()
        userMessagesViewModel = UserMessagesViewModel(getAllMessagesByUserNameUseCase)
        Dispatchers.setMain(testDispatcher)
        username = "username"
        messageResponseList = listOf(
            MessageResponse(subject = "subject", message = "message"),
            MessageResponse(subject = "subject2", message = "message2")
        )
        messageModelList = messageResponseList.map { it.toMessageModel() }
        userMessageResponse =
            UserMessageResponse(user = username, messages = messageResponseList)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when fetching messages by username the UserMessagesUiState should be Success`() = runTest {
        coEvery { getAllMessagesByUserNameUseCase.invoke(username) } returns flowOf(messageModelList)
        userMessagesViewModel.getAllMessagesByUserName(username)
        advanceUntilIdle()
        assert(userMessagesViewModel.userMessageUiState.value == UserMessagesUiState.Success(messageModelList))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun`when there is an error fetching all messages, the homeUiSate is updated with an error state`()  = runTest{
        coEvery { getAllMessagesByUserNameUseCase(username) } returns flow {
            throw Exception("error")
        }
        userMessagesViewModel.getAllMessagesByUserName(username)
        advanceUntilIdle()
        assert(userMessagesViewModel.userMessageUiState.value == UserMessagesUiState.Error)
    }
}