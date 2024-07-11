package com.lovevery.messagemanager.usermessages.presentation.uistate

sealed class SearchDialogUiState{
    data object InputError: SearchDialogUiState()
    data object Initial: SearchDialogUiState()
    data object ValidSearch: SearchDialogUiState()
    data object Cancel: SearchDialogUiState()

}