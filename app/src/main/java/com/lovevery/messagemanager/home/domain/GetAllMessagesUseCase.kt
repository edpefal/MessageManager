package com.lovevery.messagemanager.home.domain

import javax.inject.Inject

class GetAllMessagesUseCase @Inject constructor(private val homeRepository: HomeRepository ) {
    suspend operator fun invoke() = homeRepository.getAllMessages()
}