package com.lovevery.messagemanager.home.presentation

sealed class HomeUiState{
    data object Empty: HomeUiState()
    data object Loading: HomeUiState()
    data class Success(val messages: List<UserMessage>) : HomeUiState()
    data object Error: HomeUiState()

}