package com.lovevery.messagemanager.profile.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lovevery.messagemanager.addmessage.domain.AddMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SearchUserMessagesDialogViewModel @Inject constructor(private val addMessageUseCase: AddMessageUseCase) :
    ViewModel() {


    private val _inputUser = MutableLiveData<String>()
    val inputUser: LiveData<String> = _inputUser


    fun onUserText(inputText: String) {
        _inputUser.value = inputText
    }

    /*fun updateUiState(newUIState: AddMessageUiState){
        _addMessageUiSate.value = AddMessageUiState.Empty
    }*/

}

