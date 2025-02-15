package com.lovevery.messagemanager.home.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lovevery.messagemanager.home.domain.HomeRepository
import com.lovevery.messagemanager.shared.data.MessageResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeService: HomeService,
    private val gson: Gson
) :
    HomeRepository {
    override suspend fun getAllMessages(): Flow<Map<String, List<MessageResponse>>> {
        return flow {
            val apiResponse = homeService.getMessages()
            val mapType = object : TypeToken<Map<String, List<MessageResponse>>>() {}.type
            val messagesMap: Map<String, List<MessageResponse>> =
                gson.fromJson(apiResponse.body, mapType)
            emit(messagesMap)
        }.flowOn(Dispatchers.IO)
    }

}