package com.lovevery.messagemanager.usermessages.presentation.uistate

sealed class SearchDialogUiState{
    data object Initial: SearchDialogUiState()
    data object Send: SearchDialogUiState()

}