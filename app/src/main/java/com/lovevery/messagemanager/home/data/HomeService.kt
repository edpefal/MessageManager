package com.lovevery.messagemanager.home.data

import com.lovevery.messagemanager.shared.ApiResponse
import javax.inject.Inject

class HomeService @Inject constructor(private val homeClient: HomeClient) {

    suspend fun getMessages(): ApiResponse {
        return homeClient.getMessages()
    }


}