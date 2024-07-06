package com.lovevery.messagemanager.home.domain

import com.lovevery.messagemanager.home.data.MessageResponse

interface HomeRepository {
    suspend fun getAllMessages(): List<MessageResponse>
}