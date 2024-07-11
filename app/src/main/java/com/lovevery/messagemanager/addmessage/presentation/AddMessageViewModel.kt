package com.lovevery.messagemanager.addmessage.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lovevery.messagemanager.addmessage.domain.AddMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddMessageViewModel @Inject constructor(private val addMessageUseCase: AddMessageUseCase) :
    ViewModel() {

    private val _addMessageUiSate = MutableStateFlow<AddMessageUiState>(AddMessageUiState.Empty)
    val addMessageUiSate: StateFlow<AddMessageUiState> get() = _addMessageUiSate

    private val _inputUser = MutableStateFlow("")
    val inputUser: StateFlow<String> = _inputUser

    private val _inputSubject = MutableStateFlow("")
    val inputSubject: StateFlow<String> = _inputSubject

    private val _inputMessage = MutableStateFlow("")
    val inputMessage: StateFlow<String> = _inputMessage

    private val inputList = listOf(
        inputUser to InputErrorType.USERNAME_REQUIRED,
        inputSubject to InputErrorType.SUBJECT_REQUIRED,
        inputMessage to InputErrorType.MESSAGE_REQUIRED
    )

    private val invalidCharacters = listOf(" ")

    fun onUserText(inputText: String) {
        _inputUser.value = inputText
    }

    fun onSubjectText(inputText: String) {
        _inputSubject.value = inputText
    }

    fun onMessageText(inputText: String) {
        _inputMessage.value = inputText
    }

    fun updateUiState(newUIState: AddMessageUiState) {
        _addMessageUiSate.value = newUIState
    }


    fun addMessage() {
        if (isValidInput()) {
            viewModelScope.launch {
                _addMessageUiSate.value = AddMessageUiState.IsLoading(true)
                addMessageUseCase(
                    AddMessageModel(
                        inputUser.value,
                        inputSubject.value,
                        inputMessage.value
                    )
                )
                    .catch {
                        _addMessageUiSate.value = AddMessageUiState.IsLoading(false)
                        _addMessageUiSate.value = AddMessageUiState.ResponseError
                    }.collect { message ->
                        _inputMessage.value = ""
                        _inputSubject.value = ""
                        _inputUser.value = ""
                        _addMessageUiSate.value = AddMessageUiState.IsLoading(false)
                        _addMessageUiSate.value = AddMessageUiState.Success(message)
                    }
            }
        }

    }

    private fun isValidInput(): Boolean {
        for ((input, errorType) in inputList) {
            if (input.value.isEmpty()) {
                updateUiState(AddMessageUiState.InputError(errorType))
                return false
            }
            if (input == inputUser && invalidCharacters.any { it in input.value }) {
                updateUiState(AddMessageUiState.InputError(InputErrorType.INVALID_CHARACTERS))
                return false
            }
        }
        return true
    }

    fun isErrorTypeInvalidCharacters(errorType: InputErrorType) =
        errorType == InputErrorType.INVALID_CHARACTERS

    fun isErrorTypeSubject(errorType: InputErrorType) = errorType == InputErrorType.SUBJECT_REQUIRED
    fun isErrorTypeMessage(errorType: InputErrorType) = errorType == InputErrorType.MESSAGE_REQUIRED
    fun isErrorTypeInvalidCharsOrUsernameRequired(errorType: InputErrorType) =
        errorType == InputErrorType.INVALID_CHARACTERS || errorType == InputErrorType.USERNAME_REQUIRED


}

