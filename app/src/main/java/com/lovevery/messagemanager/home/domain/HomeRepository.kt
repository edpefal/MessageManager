package com.lovevery.messagemanager.home.domain

import com.lovevery.messagemanager.home.data.MessageResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getAllMessages(): Flow<Map<String, List<MessageResponse>>>
}