package com.lovevery.messagemanager.addmessage.domain

import com.lovevery.messagemanager.addmessage.data.AddMessageRequest
import com.lovevery.messagemanager.addmessage.data.AddMessageResponse
import kotlinx.coroutines.flow.Flow

interface AddMessageRepository {
    suspend fun addMessage(messageRequest: AddMessageRequest): Flow<AddMessageResponse>
}