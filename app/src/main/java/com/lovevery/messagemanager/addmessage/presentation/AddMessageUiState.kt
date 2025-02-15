package com.lovevery.messagemanager.addmessage.presentation

sealed class AddMessageUiState{
    data object Empty: AddMessageUiState()
    data class IsLoading(val isLoading: Boolean): AddMessageUiState()
    data class Success(val messageAdded: AddMessageModel) : AddMessageUiState()
    data object ResponseError: AddMessageUiState()
    data class InputError(val inputErrorType: InputErrorType): AddMessageUiState()

}