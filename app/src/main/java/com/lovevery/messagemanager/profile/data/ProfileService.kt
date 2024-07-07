package com.lovevery.messagemanager.profile.data

import com.google.gson.Gson
import javax.inject.Inject

class ProfileService @Inject constructor(
    private val profileClient: ProfileClient,
    private val gson: Gson
) {

    suspend fun getMessagesByUser(userName: String): UserMessageResponse {
        val response = profileClient.getMessagesByUser(userName)
        return gson.fromJson(response.body, UserMessageResponse::class.java)
    }


}