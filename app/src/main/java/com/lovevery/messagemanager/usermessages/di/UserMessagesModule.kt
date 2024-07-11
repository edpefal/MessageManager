package com.lovevery.messagemanager.usermessages.di

import com.lovevery.messagemanager.usermessages.data.UserMessagesClient
import com.lovevery.messagemanager.usermessages.data.UserMessagesRepositoryImpl
import com.lovevery.messagemanager.usermessages.domain.UserMessagesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
class UserMessagesModule {
    @Provides
    fun provideUserMessagesClient(retrofit: Retrofit): UserMessagesClient {
        return retrofit.create(UserMessagesClient::class.java)
    }

    @Provides
    fun provideUserMessagesRepository(userMessagesRepositoryImpl: UserMessagesRepositoryImpl): UserMessagesRepository {
        return userMessagesRepositoryImpl
    }

}