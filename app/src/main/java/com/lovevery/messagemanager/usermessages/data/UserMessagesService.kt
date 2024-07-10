package com.lovevery.messagemanager.usermessages.data

import com.lovevery.messagemanager.shared.ApiResponse
import javax.inject.Inject

class UserMessagesService @Inject constructor(
    private val userMessagesClient: UserMessagesClient,
) {

    suspend fun getMessagesByUser(userName: String): ApiResponse
        = userMessagesClient.getMessagesByUser(userName)
}