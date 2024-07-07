package com.lovevery.messagemanager.home.presentation

import com.lovevery.messagemanager.shared.presentation.UserMessageModel

sealed class HomeUiState{
    data object Empty: HomeUiState()
    data object Loading: HomeUiState()
    data class Success(val messages: List<UserMessageModel>) : HomeUiState()
    data object Error: HomeUiState()

}