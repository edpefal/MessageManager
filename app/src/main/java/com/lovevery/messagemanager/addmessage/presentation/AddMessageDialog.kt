package com.lovevery.messagemanager.addmessage.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
    val addMessageUiState by addMessageViewModel.addMessageUiSate.collectAsState(initial = AddMessageUiState.Empty)
    val textUser by addMessageViewModel.inputUser.collectAsState(initial = "")
    val textSubject by addMessageViewModel.inputSubject.collectAsState(initial = "")
    val textMessage by addMessageViewModel.inputMessage.collectAsState(initial = "")


    Dialog(onDismissRequest = {
        addMessageViewModel.updateUiState(AddMessageUiState.Empty)
        onDismissRequest()
    }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
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


                when (addMessageUiState) {
                    is AddMessageUiState.IsLoading -> {
                        DialogContent(
                            focusRequester,
                            textUser,
                            InputErrorType.NONE,
                            true,
                            addMessageViewModel,
                            textSubject,
                            textMessage,
                            onDismissRequest
                        )

                        Text(
                            text = stringResource(id = R.string.loading), modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    is AddMessageUiState.ResponseError -> {
                        DialogContent(
                            focusRequester,
                            textUser,
                            InputErrorType.NONE,
                            false,
                            addMessageViewModel,
                            textSubject,
                            textMessage,
                            onDismissRequest
                        )
                        Text(
                            text = stringResource(id = R.string.error_text), modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center,
                            style = CustomTextStyles.errorMedium
                        )
                    }

                    is AddMessageUiState.Empty -> {
                        DialogContent(
                            focusRequester,
                            textUser,
                            InputErrorType.NONE,
                            false,
                            addMessageViewModel,
                            textSubject,
                            textMessage,
                            onDismissRequest
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }


                    is AddMessageUiState.Success -> {
                        DialogContent(
                            focusRequester,
                            textUser,
                            InputErrorType.NONE,
                            false,
                            addMessageViewModel,
                            textSubject,
                            textMessage,
                            onDismissRequest
                        )
                        addMessageViewModel.updateUiState(AddMessageUiState.Empty)
                        onDismissRequest()
                    }

                    is AddMessageUiState.InputError -> {
                        val errorType =
                            (addMessageUiState as AddMessageUiState.InputError).inputErrorType
                        DialogContent(
                            focusRequester,
                            textUser,
                            errorType,
                            false,
                            addMessageViewModel,
                            textSubject,
                            textMessage,
                            onDismissRequest
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                }
            }
        }
    }
}

@Composable
private fun DialogContent(
    focusRequester: FocusRequester,
    textUser: String,
    errorType: InputErrorType,
    isLoading: Boolean,
    addMessageViewModel: AddMessageViewModel,
    textSubject: String,
    textMessage: String,
    onDismissRequest: () -> Unit
) {
    AddMessageTextField(
        focusRequester,
        textUser,
        stringResource(id = R.string.username),
        errorType == InputErrorType.USERNAME
    ) {
        addMessageViewModel.onUserText(
            it
        )
    }
    AddMessageTextField(
        focusRequester,
        textSubject,
        stringResource(id = R.string.subject),
        errorType == InputErrorType.SUBJECT
    ) {
        addMessageViewModel.onSubjectText(
            it
        )
    }
    AddMessageTextField(
        focusRequester,
        textMessage,
        stringResource(id = R.string.message),
        errorType == InputErrorType.MESSAGE
    ) {
        addMessageViewModel.onMessageText(
            it
        )
    }
    ButtonSection(addMessageViewModel, onDismissRequest, isLoading)

}

@Composable
private fun ButtonSection(
    addMessageViewModel: AddMessageViewModel,
    onDismissRequest: () -> Unit,
    showLoading: Boolean
) {
    Row {
        Spacer(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        )
        OutlinedButton(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            border = BorderStroke(color = MaterialTheme.colorScheme.tertiary, width = 1.dp),
            onClick = {
                addMessageViewModel.updateUiState(AddMessageUiState.Empty)
                onDismissRequest()
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
                addMessageViewModel.addMessage()
            }) {

            val alpha by animateFloatAsState(
                targetValue = if (showLoading) 0f else 1f,
                animationSpec = tween(durationMillis = 1000),
                label = ""
            )

            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(id = R.string.add_message),
                    modifier = Modifier.alpha(alpha),
                    color = Color.White

                )
                if (showLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(30.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                }
            }


        }
    }
}

@Composable
private fun AddMessageTextField(
    focusRequester: FocusRequester,
    textUser: String,
    label: String,
    isError: Boolean,
    onTextChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester),

        isError = isError,
        supportingText = {
            if (isError) Text(text = stringResource(id = R.string.required_field), style = CustomTextStyles.errorSmall
        ) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.tertiary,
            unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
            //disabledTextColor = Color.DarkGray,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = MaterialTheme.colorScheme.tertiary,
            focusedBorderColor = MaterialTheme.colorScheme.tertiary,
            unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,

            ),
        value = textUser,
        onValueChange = { onTextChange(it) },
        label = { Text(label) })
}