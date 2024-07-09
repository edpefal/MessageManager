package com.lovevery.messagemanager.addmessage.data

import com.google.gson.Gson
import com.lovevery.messagemanager.addmessage.domain.AddMessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AddMessageRepositoryImpl @Inject constructor(
    private val addMessageService: AddMessageService,
    private val gson: Gson
) : AddMessageRepository {

    override suspend fun addMessage(messageRequest: AddMessageRequest): Flow<AddMessageResponse> {
        return flow {
            val response = addMessageService.addMessage(messageRequest)
            val messageResponse = gson.fromJson(response.body, AddMessageResponse::class.java)
            emit(messageResponse)
        }.flowOn(Dispatchers.IO)


    }

}