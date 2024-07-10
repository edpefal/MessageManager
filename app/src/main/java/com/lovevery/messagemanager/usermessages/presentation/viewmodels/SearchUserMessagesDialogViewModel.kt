package com.lovevery.messagemanager.usermessages.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.lovevery.messagemanager.addmessage.domain.AddMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class SearchUserMessagesDialogViewModel @Inject constructor(private val addMessageUseCase: AddMessageUseCase) :
    ViewModel() {


    private val _inputUser = MutableStateFlow("")
    val inputUser: StateFlow<String> = _inputUser

    fun onUserText(inputText: String) {
        _inputUser.value = inputText
    }


}

