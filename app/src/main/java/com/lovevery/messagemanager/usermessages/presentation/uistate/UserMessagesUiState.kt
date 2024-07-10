package com.lovevery.messagemanager.usermessages.presentation.uistate

import com.lovevery.messagemanager.shared.presentation.MessageModel

sealed class UserMessagesUiState{
    data object Empty: UserMessagesUiState()
    data object Loading: UserMessagesUiState()
    data class Success(val messages: List<MessageModel>) : UserMessagesUiState()
    data object Error: UserMessagesUiState()

}