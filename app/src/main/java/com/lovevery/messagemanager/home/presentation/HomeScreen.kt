package com.lovevery.messagemanager.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.lovevery.messagemanager.R
import com.lovevery.messagemanager.shared.Routes

@Composable
fun HomeScreen(homeViewModel: HomeViewModel, navController: NavHostController) {

    LaunchedEffect(Unit) {
        homeViewModel.getAllMessages()
    }

    val homeUiState: HomeUiState by homeViewModel.homeUiSate.observeAsState(initial = HomeUiState.Empty)
    //val posts by postListViewModel.posts.observeAsState(emptyList())
    val listState = rememberLazyListState()
    Scaffold {
        Column(Modifier.padding(it)) {
            Text(
                //color = MaterialTheme.colorScheme.primary,
                text = stringResource(id = R.string.home_header),
                modifier = Modifier.padding(dimensionResource(id = R.dimen.default_margin)),
                style = MaterialTheme.typography.titleLarge,
            )
            when (homeUiState) {
                HomeUiState.Error -> Text(
                    text = stringResource(id = R.string.error_text),
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(it)
                        .fillMaxSize(), textAlign = TextAlign.Center
                )

                HomeUiState.Loading -> {
                    Text(
                        text = stringResource(id = R.string.loading),
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(it)
                            .fillMaxSize(), textAlign = TextAlign.Center
                    )

                }

                is HomeUiState.Success -> PostContent(
                    navController,
                    homeViewModel,
                    (homeUiState as HomeUiState.Success).messages,
                    listState,
                )

                HomeUiState.Empty -> Text(
                    text = stringResource(id = R.string.fetching_data),
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(it)
                        .fillMaxSize(), textAlign = TextAlign.Center
                )
            }
        }

    }
}

@Composable
fun MessageItem(
    user: String,
    message: String,
    subject: String,
    totalMessages: Int,
    onClick: () -> Unit? = {},
) {
    Card(modifier = Modifier
        .clickable {

        }
        .height(dimensionResource(id = R.dimen.message_item_height))
        .fillMaxWidth()
        .padding(
            vertical = dimensionResource(id = R.dimen.default_vertical_padding),
            horizontal = dimensionResource(id = R.dimen.default_margin)
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.message_card_elevation))
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "profile",
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.image_message_size))
                    .padding(start = 8.dp, top = 8.dp)
            )
            Text(
                text = user,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            )
            Text(
                text = stringResource(id = R.string.total_messages, totalMessages),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
        Text(
            text = stringResource(id = R.string.last_message),
            Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            fontSize = 18.sp,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = message,
            Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                .weight(1f),
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge
            //maxLines = 2
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Category:",
                style = MaterialTheme.typography.labelLarge,
                overflow = TextOverflow.Ellipsis,

                )
            Text(
                text = subject,
                modifier = Modifier.padding(start = 4.dp, end = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,

                )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            OutlinedButton(
                onClick = { onClick() },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text(text = stringResource(id = R.string.all_messages))
            }
        }

    }

}

@Composable
fun PostContent(
    navController: NavHostController,
    viewModel: HomeViewModel,
    messages: List<UserMessage>,
    listState: LazyListState,
) {

    LazyColumn(state = listState) {
        items(messages) { item ->
            MessageItem(
                item.user,
                item.messages.last().message,
                item.messages.last().subject,
                item.messages.size,
                onClick = {
                    navController.navigate(
                        Routes.ProfileScreen.createRoute(
                            item.user,
                        )
                    )
                })
        }
    }

    /* LaunchedEffect(listState.canScrollForward) {
         if (listState.canScrollForward.not() && listState.firstVisibleItemIndex > 1) {
             viewModel.fetchPosts()
         }
     }*/

}