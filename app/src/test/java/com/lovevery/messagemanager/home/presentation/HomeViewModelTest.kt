package com.lovevery.messagemanager.home.presentation

import com.lovevery.messagemanager.home.domain.GetAllMessagesUseCase
import com.lovevery.messagemanager.shared.data.MessageResponse
import com.lovevery.messagemanager.shared.presentation.UserMessageModel
import com.lovevery.messagemanager.shared.toMessageModel
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

class HomeViewModelTest {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var getAllMessagesUseCase: GetAllMessagesUseCase
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        getAllMessagesUseCase = mockk()
        homeViewModel = HomeViewModel(getAllMessagesUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when fetch all messages the homeUiSate is updated with a success state`() = runTest {
        val user = "user"
        val messageResponseList =
            listOf(
                MessageResponse("subject", "message"),
                MessageResponse("subject2", "message2")
            )
        val messageModelList = messageResponseList.map { it.toMessageModel() }
        val userMessageModelList = listOf(UserMessageModel(user, messageModelList))

        coEvery { getAllMessagesUseCase()} returns flowOf( userMessageModelList)
        homeViewModel.getAllMessages()
        advanceUntilIdle()
        assert(homeViewModel.homeUiSate.value == HomeUiState.Success(userMessageModelList))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when there is an error fetching all messages, the homeUiSate is updated with an error state`() = runTest {
        coEvery { getAllMessagesUseCase() } returns flow {
            throw Exception("error")
        }
        homeViewModel.getAllMessages()
        advanceUntilIdle()
        assert(homeViewModel.homeUiSate.value == HomeUiState.Error)
    }
}