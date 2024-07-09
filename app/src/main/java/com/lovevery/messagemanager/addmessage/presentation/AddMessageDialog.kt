package com.lovevery.messagemanager.addmessage.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.lovevery.messagemanager.R
import com.lovevery.messagemanager.ui.theme.CustomTextStyles

@Composable
fun AddMessageDialog(
    addMessageViewModel: AddMessageViewModel,
    onDismissRequest: () -> Unit,

    ) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    val addMessageUiState by addMessageViewModel.addMessageUiSate.observeAsState(initial = AddMessageUiState.Empty)
    val textUser by addMessageViewModel.inputUser.observeAsState(initial = "")
    val textSubject by addMessageViewModel.inputSubject.observeAsState(initial = "")
    val textMessage by addMessageViewModel.inputMessage.observeAsState(initial = "")


    Dialog(onDismissRequest = {
        addMessageViewModel.updateUiState(AddMessageUiState.Empty)
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
                    text = stringResource(id = R.string.add_message),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    style = MaterialTheme.typography.titleLarge,
                )
                OutlinedTextField(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = textUser,
                    onValueChange = { addMessageViewModel.onUserText(it) },
                    label = { Text("User") })
                OutlinedTextField(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .fillMaxWidth(),
                    value = textSubject,
                    onValueChange = { addMessageViewModel.onSubjectText(it) },
                    label = { Text("Subject") })
                OutlinedTextField(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .fillMaxWidth(),
                    value = textMessage,
                    onValueChange = { addMessageViewModel.onMessageText(it) },
                    label = { Text("Message") })
                Row {
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    )
                    OutlinedButton(
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                        onClick = {
                            addMessageViewModel.updateUiState(AddMessageUiState.Empty)
                            onDismissRequest()
                        }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    Button(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                        onClick = {
                            addMessageViewModel.addMessage()
                        }) {

                        val alpha by animateFloatAsState(
                            targetValue = if ((addMessageUiState is AddMessageUiState.IsLoading)) 0f else 1f,
                            animationSpec = tween(durationMillis = 1000),
                            label = ""
                        )

                        Box {
                            Text(text = stringResource(id = R.string.add_message), modifier = Modifier.alpha(alpha))
                            if((addMessageUiState is AddMessageUiState.IsLoading)) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(30.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            }
                        }


                    }
                }
                when (addMessageUiState) {
                    is AddMessageUiState.IsLoading -> {
                        Text(
                            text = stringResource(id = R.string.loading), modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    is AddMessageUiState.Error -> {
                        Text(
                            text = stringResource(id = R.string.error_text), modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center,
                            style = CustomTextStyles.errorStyle
                        )
                    }

                    is AddMessageUiState.Empty -> Spacer(modifier = Modifier.height(16.dp))

                    is AddMessageUiState.Success -> {
                        val message =
                            (addMessageUiState as AddMessageUiState.Success).messageAdded
                        addMessageViewModel.updateUiState(AddMessageUiState.Empty)
                        onDismissRequest()
                    }
                }
            }
        }
    }
}