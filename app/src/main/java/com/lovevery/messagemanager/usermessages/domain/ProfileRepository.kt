package com.lovevery.messagemanager.usermessages.domain

import com.lovevery.messagemanager.usermessages.data.UserMessageResponse
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getAllMessagesByUser(userName: String): Flow<UserMessageResponse>
}