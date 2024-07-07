package com.lovevery.messagemanager.profile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lovevery.messagemanager.profile.domain.GetAllMessagesByUserNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(private val getAllMessagesByUserNameUseCase: GetAllMessagesByUserNameUseCase) :
    ViewModel() {

    private val _profileUiSate = MutableLiveData<ProfileUiState>()
    val profileUiSate: LiveData<ProfileUiState> get() = _profileUiSate


    fun getAllMessagesByUserName(userName: String) {
        viewModelScope.launch {
            _profileUiSate.value = ProfileUiState.Loading
            getAllMessagesByUserNameUseCase(userName)
                .catch {
                    _profileUiSate.value = ProfileUiState.Error
                }.collect { messages ->
                    if (messages.isEmpty()) {
                        _profileUiSate.value = ProfileUiState.Empty
                    } else {
                        _profileUiSate.value = ProfileUiState.Success(messages)
                    }

                }
        }

    }

}