package com.lovevery.messagemanager.home.domain

import com.lovevery.messagemanager.shared.presentation.UserMessageModel
import com.lovevery.messagemanager.shared.toMessageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllMessagesUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(): Flow<List<UserMessageModel>> =
        homeRepository.getAllMessages().map { messageMap ->
            messageMap.map {(user, message) ->
                UserMessageModel(
                    user, message.map {
                        it.toMessageModel()
                    }
                )
            }
        }

}

