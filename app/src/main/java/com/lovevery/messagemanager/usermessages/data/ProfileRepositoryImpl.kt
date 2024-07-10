package com.lovevery.messagemanager.usermessages.data

import com.google.gson.Gson
import com.lovevery.messagemanager.usermessages.domain.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val profileService: ProfileService, private val gson: Gson) :
    ProfileRepository {
    override suspend fun getAllMessagesByUser(userName: String): Flow<UserMessageResponse> {
        return flow {
            val apiResponse = profileService.getMessagesByUser(userName)
            val response = gson.fromJson(apiResponse.body, UserMessageResponse::class.java)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

}