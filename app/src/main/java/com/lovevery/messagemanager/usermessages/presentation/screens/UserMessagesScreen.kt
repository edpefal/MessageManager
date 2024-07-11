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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.lovevery.messagemanager.R
import com.lovevery.messagemanager.shared.composables.ExtraSmallPadding
import com.lovevery.messagemanager.shared.composables.Header
import com.lovevery.messagemanager.shared.composables.MediumPadding
import com.lovevery.messagemanager.shared.composables.SmallPadding
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
    val composition by rememberLottieComposition(LottieCompositionSpec.Url(stringResource(id = R.string.user_messages_animation_url)))
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
                    Column {
                        Text(
                            modifier = Modifier
                                .padding(MediumPadding()),
                            text = stringResource(id = R.string.no_messages, userName),
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )
                        LottieAnimation(
                            composition = composition,
                            iterations = Int.MAX_VALUE,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .size(dimensionResource(id = R.dimen.animation_size))
                        )
                    }
                }

                UserMessagesUiState.Loading -> Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = stringResource(id = R.string.loading))
                }

                is UserMessagesUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_profile),
                            contentDescription = stringResource(id = R.string.profile_image),
                            modifier = Modifier
                                .size(dimensionResource(id = R.dimen.profile_image_size))
                                .align(Alignment.CenterHorizontally)
                        )
                        Text(
                            userName,
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.CenterHorizontally)

                        )
                        Text(
                            stringResource(id = R.string.messages),
                            modifier = Modifier.padding(horizontal = MediumPadding(), vertical = MediumPadding()),
                            style = MaterialTheme.typography.titleLarge
                        )
                        (postUiState as UserMessagesUiState.Success).messages.forEach { item ->
                            UserMessageItem(message = item.message, subject = item.subject)
                        }
                    }
                }

                UserMessagesUiState.Empty -> Text(text = "Empty")
            }

        }

    }
}

@Composable
fun UserMessageItem(
    message: String,
    subject: String,
) {
    Card(modifier = Modifier
        .clickable {

        }
        .fillMaxWidth()
        .padding(
            vertical = MediumPadding(),
            horizontal = MediumPadding()
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.message_card_elevation))
    ) {
        Text(
            text = message,
            Modifier
                .padding(start = MediumPadding(), end = MediumPadding(), top = SmallPadding()),
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = SmallPadding()), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.category),
                style = MaterialTheme.typography.labelLarge,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subject,
                modifier = Modifier.padding(start = ExtraSmallPadding(), end = MediumPadding()),
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}

