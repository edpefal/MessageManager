package com.lovevery.messagemanager.usermessages.domain

import com.lovevery.messagemanager.shared.data.MessageResponse
import com.lovevery.messagemanager.shared.presentation.MessageModel
import com.lovevery.messagemanager.shared.toMessageModel
import com.lovevery.messagemanager.usermessages.data.UserMessageResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetAllMessagesByUserNameUseCaseTest {
    private lateinit var messageResponseList: List<MessageResponse>
    private lateinit var messageModelList: List<MessageModel>
    private lateinit var userMessageResponse: UserMessageResponse
    private lateinit var username: String
    private lateinit var userMessagesRepository: UserMessagesRepository
    private lateinit var getAllMessagesByUserNameUseCase: GetAllMessagesByUserNameUseCase


    @Before
    fun setup() {
        userMessagesRepository = mockk()
        getAllMessagesByUserNameUseCase = GetAllMessagesByUserNameUseCase(userMessagesRepository)

        username = "username"
        messageResponseList = listOf(
            MessageResponse(subject = "subject", message = "message"),
            MessageResponse(subject = "subject2", message = "message2")
        )
        messageModelList = messageResponseList.map { it.toMessageModel() }
        userMessageResponse =
            UserMessageResponse(user = username, messages = messageResponseList)
    }

    @Test
    fun `when fetching all the messages for an user, then the user field of the repository response should match username`() =
        runBlocking {
            coEvery { userMessagesRepository.getAllMessagesByUser(username) } returns flowOf(
                userMessageResponse
            )
            userMessagesRepository.getAllMessagesByUser(username).collect {
                assert(it.user == username)
            }
            getAllMessagesByUserNameUseCase(username).collect {
                assert(it == messageModelList)

            }
        }

    @Test
    fun `when fetching all the messages for an user, getAllMessagesByUserNameUseCase should return a list of MessageModel`() =
        runBlocking {
            coEvery { userMessagesRepository.getAllMessagesByUser(username) } returns flowOf(
                userMessageResponse
            )
            getAllMessagesByUserNameUseCase(username).collect {
                assert(it == messageModelList)

            }
        }

}