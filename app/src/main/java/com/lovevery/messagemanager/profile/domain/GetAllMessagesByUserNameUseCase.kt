package com.lovevery.messagemanager.profile.domain

import com.lovevery.messagemanager.shared.presentation.MessageModel
import com.lovevery.messagemanager.shared.toMessageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllMessagesByUserNameUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(userName: String): Flow<List<MessageModel>> =
        profileRepository.getAllMessagesByUser(userName).map { userMessagesResponse ->
            userMessagesResponse.messages.map { message ->
                message.toMessageModel()
            }
        }
}

