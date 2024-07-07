package com.lovevery.messagemanager.shared.presentation

import com.lovevery.messagemanager.shared.presentation.MessageModel

data class UserMessageModel(
    val user: String,
    val messages: List<MessageModel>
)
