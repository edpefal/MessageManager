package com.lovevery.messagemanager.profile.domain

import com.lovevery.messagemanager.profile.data.UserMessageResponse
import com.lovevery.messagemanager.shared.data.MessageResponse
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getAllMessagesByUser(userName: String): Flow<UserMessageResponse>
}