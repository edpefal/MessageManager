package com.lovevery.messagemanager.usermessages.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lovevery.messagemanager.usermessages.domain.GetAllMessagesByUserNameUseCase
import com.lovevery.messagemanager.usermessages.presentation.uistate.UserMessagesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserMessagesViewModel @Inject constructor(
    private val getAllMessagesByUserNameUseCase: GetAllMessagesByUserNameUseCase
) : ViewModel() {

    private val _userMessageUiSate = MutableStateFlow<UserMessagesUiState>(UserMessagesUiState.Empty)
    val userMessageUiState: StateFlow<UserMessagesUiState> get() = _userMessageUiSate


    fun getAllMessagesByUserName(userName: String) {
        viewModelScope.launch {
            _userMessageUiSate.value = UserMessagesUiState.Loading
            getAllMessagesByUserNameUseCase(userName)
                .catch {
                    _userMessageUiSate.value = UserMessagesUiState.Error
                }.collect { messages ->
                    if (messages.isEmpty()) {
                        _userMessageUiSate.value = UserMessagesUiState.Empty
                    } else {
                        _userMessageUiSate.value = UserMessagesUiState.Success(messages)
                    }

                }
        }

    }

}