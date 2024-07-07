package com.lovevery.messagemanager.home.domain

import com.lovevery.messagemanager.home.data.MessageResponse
import com.lovevery.messagemanager.home.presentation.MessageModel
import com.lovevery.messagemanager.home.presentation.UserMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllMessagesUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(): Flow<List<UserMessage>> =
        homeRepository.getAllMessages().map { messageMap ->
            messageMap.map {(user, message) ->
                UserMessage(
                    user, message.map {
                        it.toMessageModel()
                    }
                )
            }
        }


    private fun MessageResponse.toMessageModel() =
        MessageModel(
            subject = this.subject.orEmpty(),
            message = this.message.orEmpty(),
        )

}

