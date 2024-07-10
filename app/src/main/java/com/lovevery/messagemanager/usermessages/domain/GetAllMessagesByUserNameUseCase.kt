package com.lovevery.messagemanager.usermessages.domain

import com.lovevery.messagemanager.shared.presentation.MessageModel
import com.lovevery.messagemanager.shared.toMessageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllMessagesByUserNameUseCase @Inject constructor(private val userMessagesRepository: UserMessagesRepository) {
    suspend operator fun invoke(userName: String): Flow<List<MessageModel>> =
        userMessagesRepository.getAllMessagesByUser(userName).map { userMessagesResponse ->
            userMessagesResponse.messages.map { message ->
                message.toMessageModel()
            }
        }
}

