package com.lovevery.messagemanager.profile.data

import com.lovevery.messagemanager.profile.domain.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val profileService: ProfileService) :
    ProfileRepository {
    override suspend fun getAllMessagesByUser(userName: String): Flow<UserMessageResponse> {
        return flow {
            val apiResponse = profileService.getMessagesByUser(userName)
            emit(apiResponse)
        }.flowOn(Dispatchers.IO)
    }

}