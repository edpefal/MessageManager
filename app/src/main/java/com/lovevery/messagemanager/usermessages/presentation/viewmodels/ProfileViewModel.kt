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
class ProfileViewModel @Inject constructor(private val getAllMessagesByUserNameUseCase: GetAllMessagesByUserNameUseCase) :
    ViewModel() {

    private val _profileUiSate = MutableStateFlow<UserMessagesUiState>(UserMessagesUiState.Empty)
    val profileUiSate: StateFlow<UserMessagesUiState> get() = _profileUiSate


    fun getAllMessagesByUserName(userName: String) {
        viewModelScope.launch {
            _profileUiSate.value = UserMessagesUiState.Loading
            getAllMessagesByUserNameUseCase(userName)
                .catch {
                    _profileUiSate.value = UserMessagesUiState.Error
                }.collect { messages ->
                    if (messages.isEmpty()) {
                        _profileUiSate.value = UserMessagesUiState.Empty
                    } else {
                        _profileUiSate.value = UserMessagesUiState.Success(messages)
                    }

                }
        }

    }

}