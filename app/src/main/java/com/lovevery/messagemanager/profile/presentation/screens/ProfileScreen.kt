package com.lovevery.messagemanager.profile.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.lovevery.messagemanager.profile.presentation.uistate.ProfileUiState
import com.lovevery.messagemanager.profile.presentation.viewmodels.ProfileViewModel
import com.lovevery.messagemanager.shared.widgets.Header
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    userName: String,
    navController: NavHostController,

    ) {
    profileViewModel.getAllMessagesByUserName(userName)
    val postUiState: ProfileUiState by profileViewModel.profileUiSate.observeAsState(
        initial = ProfileUiState.Loading
    )
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            Header(stringResource(id = R.string.header_profile)) {
                coroutineScope.launch {
                    navController.popBackStack()
                }

            }
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
            when (postUiState) {
                ProfileUiState.Error -> Text(text = "Error")
                ProfileUiState.Loading -> Text(text = "Loading")
                is ProfileUiState.Success -> {
                    Column {
                        (postUiState as ProfileUiState.Success).messages.forEach { item ->
                            ProfileMessageItem(message = item.message, subject = item.subject)
                        }
                    }
                }

                ProfileUiState.Empty -> Text(text = "Empty")
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

