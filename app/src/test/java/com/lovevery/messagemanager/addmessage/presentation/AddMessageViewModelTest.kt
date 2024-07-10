package com.lovevery.messagemanager.addmessage.presentation

import com.lovevery.messagemanager.addmessage.domain.AddMessageUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class AddMessageViewModelTest {

    private lateinit var addMessageViewModel: AddMessageViewModel
    private lateinit var addMessageUseCase: AddMessageUseCase
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        addMessageUseCase = mockk()
        addMessageViewModel = AddMessageViewModel(addMessageUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `when user types the username inputUser gets updated`() {
        val username = "username"
        addMessageViewModel.onUserText(username)
        assert(addMessageViewModel.inputUser.value == username)
    }

    @Test
    fun `when user types the subject inputSubject gets updated`() {
        val subject = "subject"
        addMessageViewModel.onSubjectText(subject)
        assert(addMessageViewModel.inputSubject.value == subject)
    }

    @Test
    fun `when user types the message inputMessage gets updated`() {
        val message = "message"
        addMessageViewModel.onSubjectText(message)
        assert(addMessageViewModel.inputSubject.value == message)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when a message is added the UiState is updated with a success state`() = runTest {
        val addMessageModel = AddMessageModel("user", "subject", "message")
        coEvery { addMessageUseCase(any()) } returns flow {
            emit(addMessageModel)
        }

        addMessageViewModel.addMessage()
        advanceUntilIdle()

        assert(
            addMessageViewModel.addMessageUiSate.value == AddMessageUiState.Success(
                addMessageModel
            )
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when a message is not added the UiState is updated with an error state`() = runTest {
        coEvery { addMessageUseCase(any()) } returns flow {
            throw Exception("error")
        }

        addMessageViewModel.addMessage()
        advanceUntilIdle()

        assert(
            addMessageViewModel.addMessageUiSate.value == AddMessageUiState.Error
        )
    }

}