package com.lovevery.messagemanager.usermessages.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.lovevery.messagemanager.usermessages.presentation.uistate.SearchDialogUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class SearchUserMessagesDialogViewModel @Inject constructor() :
    ViewModel() {

    private val _searchDialogUiSate = MutableStateFlow<SearchDialogUiState>(SearchDialogUiState.Initial)
    val searchDialogUiSate: StateFlow<SearchDialogUiState> get() = _searchDialogUiSate


    private val _inputUser = MutableStateFlow("")
    val inputUser: StateFlow<String> = _inputUser

    fun onUserText(inputText: String) {
        _inputUser.value = inputText
    }

    private fun isValidSearch() = inputUser.value.isNotEmpty()

    fun updateUiState(uiState: SearchDialogUiState){
        _searchDialogUiSate.value = uiState
    }

    private fun validSearch(){
        _searchDialogUiSate.value = SearchDialogUiState.ValidSearch
    }

    fun handleCancel(){
        _inputUser.value = ""
        updateUiState(SearchDialogUiState.Cancel)
    }

    fun search(){
        if(isValidSearch()) {
            validSearch()
        } else {
            updateUiState(SearchDialogUiState.InputError)
        }
    }


}

