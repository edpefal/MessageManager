package com.lovevery.messagemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lovevery.messagemanager.addmessage.presentation.AddMessageViewModel
import com.lovevery.messagemanager.home.presentation.HomeScreen
import com.lovevery.messagemanager.home.presentation.HomeViewModel
import com.lovevery.messagemanager.profile.presentation.ProfileScreen
import com.lovevery.messagemanager.profile.presentation.ProfileViewModel
import com.lovevery.messagemanager.shared.Routes
import com.lovevery.messagemanager.ui.theme.MessageManagerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val addMessageViewModel: AddMessageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MessageManagerTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.HomeScreen.route) {
                    composable(Routes.HomeScreen.route) {
                        HomeScreen(homeViewModel, addMessageViewModel, navController)
                    }
                    composable(
                        Routes.ProfileScreen.route,
                        arguments = listOf(
                            navArgument("userName") { type = NavType.StringType },
                        )
                    ) {
                        ProfileScreen(
                            profileViewModel,
                            it.arguments?.getString("userName").orEmpty(),
                            navController
                        )
                    }
                }
            }
        }
    }
}