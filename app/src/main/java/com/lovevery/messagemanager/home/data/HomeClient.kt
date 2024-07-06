package com.lovevery.messagemanager.home.data

import retrofit2.http.GET

interface HomeClient {

    @GET("messages")
    fun getPosts(): List<MessageResponse>

}
