package com.lovevery.messagemanager.usermessages.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.lovevery.messagemanager.R
import com.lovevery.messagemanager.shared.widgets.Header
import com.lovevery.messagemanager.usermessages.presentation.uistate.UserMessagesUiState
import com.lovevery.messagemanager.usermessages.presentation.viewmodels.UserMessagesViewModel
import kotlinx.coroutines.launch

@Composable
fun UserMessagesScreen(
    userMessagesViewModel: UserMessagesViewModel,
    userName: String,
    navController: NavHostController,

    ) {
    userMessagesViewModel.getAllMessagesByUserName(userName)
    val postUiState: UserMessagesUiState by userMessagesViewModel.userMessageUiState.collectAsState(
        initial = UserMessagesUiState.Loading
    )
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Header(stringResource(id = R.string.header_user_messages)) {
                coroutineScope.launch {
                    navController.popBackStack()
                }

            }
            when (postUiState) {
                UserMessagesUiState.Error -> Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,

                ) {
                    Text(
                        modifier = Modifier

                            .padding(16.dp),
                        text = stringResource(id = R.string.no_messages),
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                }

                UserMessagesUiState.Loading -> Text(text = "Loading")
                is UserMessagesUiState.Success -> {
                    Column(
                        modifier = Modifier
                            //.padding(paddingValues)
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.ic_profile),
                            contentDescription = "profile",
                            modifier = Modifier
                                .size(150.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Text(
                            userName,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.CenterHorizontally)

                        )
                        Text(
                            stringResource(id = R.string.messages),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        (postUiState as UserMessagesUiState.Success).messages.forEach { item ->
                            ProfileMessageItem(message = item.message, subject = item.subject)
                        }
                    }
                }

                UserMessagesUiState.Empty -> Text(text = "Empty")
            }

        }

    }
}

@Composable
fun ProfileMessageItem(
    message: String,
    subject: String,
) {
    Card(modifier = Modifier
        .clickable {

        }
        //.height(dimensionResource(id = R.dimen.profile_message_item_height))
        .fillMaxWidth()
        .padding(
            vertical = dimensionResource(id = R.dimen.default_vertical_padding),
            horizontal = dimensionResource(id = R.dimen.default_margin)
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.message_card_elevation))
    ) {
        Text(
            text = message,
            Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp),
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.category),
                style = MaterialTheme.typography.labelLarge,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subject,
                modifier = Modifier.padding(start = 4.dp, end = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}

