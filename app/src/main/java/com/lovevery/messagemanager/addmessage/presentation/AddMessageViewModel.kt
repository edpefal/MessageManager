package com.lovevery.messagemanager.addmessage.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lovevery.messagemanager.addmessage.domain.AddMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddMessageViewModel @Inject constructor(private val addMessageUseCase: AddMessageUseCase) :
    ViewModel() {

    private val _addMessageUiSate = MutableLiveData<AddMessageUiState>()
    val addMessageUiSate: LiveData<AddMessageUiState> get() = _addMessageUiSate

    private val _inputUser = MutableLiveData<String>()
    val inputUser: LiveData<String> = _inputUser

    private val _inputSubject = MutableLiveData<String>()
    val inputSubject: LiveData<String> = _inputSubject

    private val _inputMessage = MutableLiveData<String>()
    val inputMessage: LiveData<String> = _inputMessage

    fun onUserText(inputText: String) {
        _inputUser.value = inputText
    }

    fun onSubjectText(inputText: String) {
        _inputSubject.value = inputText
    }

    fun onMessageText(inputText: String) {
        _inputMessage.value = inputText
    }

    fun updateUiState(newUIState: AddMessageUiState){
        _addMessageUiSate.value = AddMessageUiState.Empty
    }


    fun addMessage() {
        viewModelScope.launch {
            _addMessageUiSate.value = AddMessageUiState.IsLoading(true)
            addMessageUseCase(
                AddMessageModel(
                    inputUser.value.orEmpty(),
                    inputSubject.value.orEmpty(),
                    inputMessage.value.orEmpty()
                )
            )
                .catch {
                    _addMessageUiSate.value = AddMessageUiState.IsLoading(false)
                    _addMessageUiSate.value = AddMessageUiState.Error
                }.collect { message ->
                    _addMessageUiSate.value = AddMessageUiState.IsLoading(false)
                    _addMessageUiSate.value = AddMessageUiState.Success(message)
                }
        }
    }

}

