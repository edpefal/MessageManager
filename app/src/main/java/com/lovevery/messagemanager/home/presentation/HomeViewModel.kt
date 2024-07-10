package com.lovevery.messagemanager.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lovevery.messagemanager.home.domain.GetAllMessagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val getAllMessagesUseCase: GetAllMessagesUseCase) :
    ViewModel() {

    private val _homeUiSate = MutableStateFlow<HomeUiState>(HomeUiState.Empty)
    val homeUiSate: StateFlow<HomeUiState> get() = _homeUiSate


    fun getAllMessages() {
        viewModelScope.launch {
            _homeUiSate.value = HomeUiState.Loading
            getAllMessagesUseCase()
                .catch {
                    _homeUiSate.value = HomeUiState.Error
                }.collect { messages ->
                    if (messages.isEmpty()) {
                        _homeUiSate.value = HomeUiState.Empty
                    } else {
                        _homeUiSate.value = HomeUiState.Success(messages)
                    }

                }
        }

    }

}