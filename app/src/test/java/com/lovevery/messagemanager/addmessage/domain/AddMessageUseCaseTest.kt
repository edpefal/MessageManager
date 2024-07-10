package com.lovevery.messagemanager.addmessage.domain

import com.lovevery.messagemanager.addmessage.data.AddMessageRequest
import com.lovevery.messagemanager.addmessage.data.AddMessageResponse
import com.lovevery.messagemanager.addmessage.presentation.AddMessageModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddMessageUseCaseTest {

    private lateinit var addMessageResponse: AddMessageResponse
    private lateinit var addMessageRequest: AddMessageRequest
    private lateinit var addMessageModel: AddMessageModel
    private lateinit var addMessageUseCase: AddMessageUseCase
    private lateinit var messageRepository: AddMessageRepository

    @Before
    fun setUp() {
        messageRepository = mockk()
        addMessageUseCase = AddMessageUseCase(messageRepository)
        addMessageModel =
            AddMessageModel(user = "user", subject = "subject", message = "message")
        addMessageRequest = AddMessageRequest(
            user = "user",
            subject = "subject",
            message = "message",
            operation = "add_message"
        )
        addMessageResponse =
            AddMessageResponse(user = "user", subject = "subject", message = "message")
    }

    @Test
    fun `when adding a message, addMessage in the repository is called`() = runBlocking {
        coEvery { messageRepository.addMessage(any()) } returns flowOf(addMessageResponse)
        addMessageUseCase.invoke(addMessageModel)
        coVerify { messageRepository.addMessage(addMessageRequest) }
    }

    @Test
    fun `when adding a message, the message model should match the response `() = runBlocking {
        coEvery { messageRepository.addMessage(any()) } returns flowOf(addMessageResponse)
        val response = addMessageUseCase.invoke(addMessageModel)
        response.collect {
            assert(it == addMessageModel)
        }
    }
}