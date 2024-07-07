package com.lovevery.messagemanager.shared

sealed class Routes(val route: String) {
    data object HomeScreen : Routes("home_screen")
    //TODO not need it
    /*data object PostDetailScreen : Routes("post_detail_screen/{postId}/{userName}/{userId}/{postContent}") {
        fun createRoute(postId: String, userName: String, userId: String, postContent: String) =
            "post_detail_screen/$postId/$userName/$userId/$postContent"
    }*/
    data object ProfileScreen : Routes("profile_screen/{userName}") {
        fun createRoute(userName: String) = "profile_screen/$userName"

    }


}