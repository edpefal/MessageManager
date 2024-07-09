package com.lovevery.messagemanager.profile.data

import com.google.gson.Gson
import com.lovevery.messagemanager.shared.ApiResponse
import javax.inject.Inject

class ProfileService @Inject constructor(
    private val profileClient: ProfileClient,
) {

    suspend fun getMessagesByUser(userName: String): ApiResponse
        = profileClient.getMessagesByUser(userName)
}