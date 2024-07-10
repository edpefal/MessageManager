package com.lovevery.messagemanager.usermessages.data

import com.google.gson.Gson
import com.lovevery.messagemanager.usermessages.domain.UserMessagesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserMessagesRepositoryImpl @Inject constructor(
    private val userMessagesService: UserMessagesService,
    private val gson: Gson
) :
    UserMessagesRepository {
    override suspend fun getAllMessagesByUser(userName: String): Flow<UserMessageResponse> {
        return flow {
            val apiResponse = userMessagesService.getMessagesByUser(userName)
            val response = gson.fromJson(apiResponse.body, UserMessageResponse::class.java)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

}