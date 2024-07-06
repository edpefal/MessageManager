package com.lovevery.messagemanager.home.data

import com.lovevery.messagemanager.home.domain.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val homeService: HomeService) :
    HomeRepository {
    override suspend fun getAllMessages(): List<MessageResponse> = homeService.getMessages()

}