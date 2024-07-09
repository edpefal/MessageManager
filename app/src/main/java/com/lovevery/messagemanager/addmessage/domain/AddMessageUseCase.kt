package com.lovevery.messagemanager.addmessage.domain

import com.lovevery.messagemanager.addmessage.data.AddMessageRequest
import com.lovevery.messagemanager.addmessage.data.AddMessageResponse
import com.lovevery.messagemanager.addmessage.presentation.AddMessageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val ADD_OPERATION = "add_message"

class AddMessageUseCase @Inject constructor(private val addMessageRepository: AddMessageRepository) {

    suspend operator fun invoke(addMessageModel: AddMessageModel): Flow<AddMessageModel> =
        addMessageRepository.addMessage(addMessageModel.toAddMessageRequest()).map {
            it.toAddMessageModel()
        }

    private fun AddMessageModel.toAddMessageRequest() = AddMessageRequest(
        user = this.user,
        subject = this.subject,
        message = this.message,
        operation = ADD_OPERATION

    )

    private fun AddMessageResponse.toAddMessageModel() = AddMessageModel(
        user = this.user.orEmpty(),
        subject = this.subject.orEmpty(),
        message = this.message.orEmpty()
    )

}

