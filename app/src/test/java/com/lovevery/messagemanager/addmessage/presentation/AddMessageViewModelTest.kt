package com.lovevery.messagemanager.addmessage.presentation

import com.lovevery.messagemanager.addmessage.domain.AddMessageUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class AddMessageViewModelTest {

    private lateinit var addMessageViewModel: AddMessageViewModel
    private lateinit var addMessageUseCase: AddMessageUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        addMessageUseCase = mockk()
        addMessageViewModel = AddMessageViewModel(addMessageUseCase)
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
    fun `when a message is added the UiState is updates with a success state`() = runBlocking {
        Dispatchers.setMain(testDispatcher)

        val username = "username"
        val subject = "subject"
        val message = "message"

        val addMessageModel = AddMessageModel(username, subject, message)

        addMessageViewModel.onSubjectText(subject)
        addMessageViewModel.onMessageText(message)
        addMessageViewModel.onUserText(username)

        coEvery { addMessageUseCase.invoke(addMessageModel) } returns flowOf(addMessageModel)

        addMessageViewModel.addMessage()

        assert(
            addMessageViewModel.addMessageUiSate.value == AddMessageUiState.Success(
                addMessageModel
            )
        )
    }

}