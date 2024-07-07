package com.lovevery.messagemanager.home.data

import com.lovevery.messagemanager.shared.ApiResponse
import retrofit2.http.GET

interface HomeClient {

    @GET("messages")
    suspend fun getMessages(): ApiResponse

}
