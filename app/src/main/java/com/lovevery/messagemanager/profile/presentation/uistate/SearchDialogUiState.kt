package com.lovevery.messagemanager.profile.presentation.uistate

sealed class SearchDialogUiState{
    data object Initial: SearchDialogUiState()
    data object Send: SearchDialogUiState()

}