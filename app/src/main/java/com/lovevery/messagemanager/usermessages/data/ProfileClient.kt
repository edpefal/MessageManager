package com.lovevery.messagemanager.usermessages.data

import com.lovevery.messagemanager.shared.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileClient {
    @GET("messages/{userName}")
    suspend fun getMessagesByUser(@Path("userName") userName: String): ApiResponse
}