package com.lovevery.messagemanager.usermessages.presentation.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.lovevery.messagemanager.R
import com.lovevery.messagemanager.ui.theme.CustomTextStyles
import com.lovevery.messagemanager.usermessages.presentation.uistate.SearchDialogUiState
import com.lovevery.messagemanager.usermessages.presentation.viewmodels.SearchUserMessagesDialogViewModel

@Composable
fun SearchUserMessagesDialog(
    searchUserMessagesViewModel: SearchUserMessagesDialogViewModel,
    onDismissRequest: () -> Unit,
    onSearch: (String) -> Unit
) {
    val textUser by searchUserMessagesViewModel.inputUser.collectAsState(initial = "")
    val searchDialogUiState: SearchDialogUiState by searchUserMessagesViewModel.searchDialogUiSate.collectAsState(
        initial = SearchDialogUiState.Initial
    )
    Dialog(onDismissRequest = {
        searchUserMessagesViewModel.handleCancel()
    }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            contentColor = Color.LightGray
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.search),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    style = MaterialTheme.typography.titleLarge,
                )
                when (searchDialogUiState) {
                    SearchDialogUiState.Initial -> {
                        DialogContent(false, textUser, searchUserMessagesViewModel)
                    }

                    SearchDialogUiState.InputError -> {
                        DialogContent(true, textUser, searchUserMessagesViewModel)
                    }

                    SearchDialogUiState.ValidSearch -> {
                        onSearch(textUser)
                        searchUserMessagesViewModel.updateUiState(SearchDialogUiState.Initial)
                        onDismissRequest()
                    }

                    SearchDialogUiState.Cancel -> {
                        searchUserMessagesViewModel.updateUiState(SearchDialogUiState.Initial)
                        onDismissRequest()
                    }
                }


                Row(Modifier.padding(bottom = 16.dp)) {
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    )
                    OutlinedButton(
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                        border = BorderStroke(
                            color = MaterialTheme.colorScheme.tertiary,
                            width = 1.dp
                        ),
                        onClick = {
                            searchUserMessagesViewModel.handleCancel()
                        }) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                    Button(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = Color.White
                        ),
                        onClick = {
                            searchUserMessagesViewModel.search()
                        }) {
                        Box {
                            Text(text = stringResource(id = R.string.search), color = Color.White)
                        }
                    }
                }

            }
        }
    }
}

@Composable
private fun DialogContent(
    showError: Boolean,
    textUser: String,
    searchUserMessagesViewModel: SearchUserMessagesDialogViewModel
) {
    OutlinedTextField(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .fillMaxWidth(),
        isError = showError,
        supportingText = {
            if (showError) Text(
                text = stringResource(id = R.string.required_field),
                style = CustomTextStyles.errorSmall
            )
        },
        value = textUser,
        onValueChange = { searchUserMessagesViewModel.onUserText(it) },
        label = { Text(stringResource(id = R.string.type_username)) }
    )
}