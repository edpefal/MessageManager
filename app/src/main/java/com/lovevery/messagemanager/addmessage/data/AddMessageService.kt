package com.lovevery.messagemanager.addmessage.data

import com.lovevery.messagemanager.shared.ApiResponse
import javax.inject.Inject

class AddMessageService @Inject constructor(private val addMessageClient: AddMessageClient) {

    suspend fun addMessage(messageRequest: AddMessageRequest): ApiResponse {
        return addMessageClient.addMessage(messageRequest)
    }


}