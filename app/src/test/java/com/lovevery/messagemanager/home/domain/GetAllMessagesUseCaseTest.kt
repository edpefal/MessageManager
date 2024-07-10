package com.lovevery.messagemanager.home.domain

import com.lovevery.messagemanager.shared.data.MessageResponse
import com.lovevery.messagemanager.shared.presentation.UserMessageModel
import com.lovevery.messagemanager.shared.toMessageModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetAllMessagesUseCaseTest {
    private lateinit var getAllMessagesUseCase: GetAllMessagesUseCase
    private lateinit var homeRepository: HomeRepository

    @Before
    fun setUp() {
        homeRepository = mockk()
        getAllMessagesUseCase = GetAllMessagesUseCase(homeRepository)
    }

    @Test
    fun `when fetch all messages, getAllMessagesUseCase should return a list of UserMessageModel`() = runBlocking {
        val user = "user"
        val messageResponseList =
            listOf(
                MessageResponse("subject", "message"),
                MessageResponse("subject2", "message2")
            )
        val messagesMap = mapOf(user to messageResponseList)
        val messageModelList = messageResponseList.map { it.toMessageModel() }
        val userMessageModelList = listOf(UserMessageModel(user, messageModelList))

        coEvery { homeRepository.getAllMessages() } returns flowOf(messagesMap)
        getAllMessagesUseCase.invoke().collect{
            assert(it == userMessageModelList)
        }
    }
}