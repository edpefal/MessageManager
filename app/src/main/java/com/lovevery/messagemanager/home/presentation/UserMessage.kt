package com.lovevery.messagemanager.home.presentation

data class UserMessage(
    val user: String,
    val messages: List<MessageModel>
)
