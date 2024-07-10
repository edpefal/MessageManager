package com.lovevery.messagemanager.shared

sealed class Routes(val route: String) {
    data object HomeScreen : Routes("home_screen")
    data object ProfileScreen : Routes("profile_screen/{userName}") {
        fun createRoute(userName: String) = "profile_screen/$userName"

    }


}