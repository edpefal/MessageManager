package com.lovevery.messagemanager.usermessages.presentation.dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.lovevery.messagemanager.R
import com.lovevery.messagemanager.usermessages.presentation.viewmodels.SearchUserMessagesDialogViewModel

@Composable
fun SearchUserMessagesDialog(
    searchUserMessagesViewModel: SearchUserMessagesDialogViewModel,
    onDismissRequest: () -> Unit,
    onSearch: (String) -> Unit
) {
    //val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        //focusRequester.requestFocus()
    }
    //val addMessageUiState by searchUserMessagesViewModel.addMessageUiSate.observeAsState(initial = SearchDialogUiState.Initial)
    val textUser by searchUserMessagesViewModel.inputUser.observeAsState(initial = "")


    Dialog(onDismissRequest = {
        //searchUserMessagesViewModel.updateUiState(SearchDialogUiState.Initial)
        onDismissRequest()
    }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            //.height(500.dp),
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
                OutlinedTextField(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .fillMaxWidth(),
                    //.focusRequester(focusRequester),
                    value = textUser,
                    onValueChange = { searchUserMessagesViewModel.onUserText(it) },
                    label = { Text(stringResource(id = R.string.type_username)) })

                Row(Modifier.padding(bottom = 16.dp)) {
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    )
                    OutlinedButton(
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                        onClick = {
                            //searchUserMessagesViewModel.updateUiState(AddMessageUiState.Empty)
                            onDismissRequest()
                        }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    Button(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                        onClick = {
                            onSearch(textUser)
                            onDismissRequest()
                        }) {

                        Box {
                            Text(text = stringResource(id = R.string.search))
                        }
                    }
                }

            }
        }
    }
}