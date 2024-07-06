package com.lovevery.messagemanager.home.data

import javax.inject.Inject

class HomeService @Inject constructor(private val homeClient: HomeClient) {

    fun getMessages(): List<MessageResponse> {
        return homeClient.getPosts()
    }


}