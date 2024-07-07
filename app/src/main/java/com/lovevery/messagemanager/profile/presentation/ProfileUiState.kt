package com.lovevery.messagemanager.profile.presentation

import com.lovevery.messagemanager.shared.presentation.MessageModel

sealed class ProfileUiState{
    data object Empty: ProfileUiState()
    data object Loading: ProfileUiState()
    data class Success(val messages: List<MessageModel>) : ProfileUiState()
    data object Error: ProfileUiState()

}