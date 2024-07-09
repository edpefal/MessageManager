package com.lovevery.messagemanager.addmessage.data

import com.lovevery.messagemanager.shared.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AddMessageClient {

    @POST("messages")
    suspend fun addMessage(@Body messageRequest: AddMessageRequest): ApiResponse

}
