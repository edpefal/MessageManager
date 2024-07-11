package com.lovevery.messagemanager.addmessage.di

import com.lovevery.messagemanager.addmessage.data.AddMessageClient
import com.lovevery.messagemanager.addmessage.data.AddMessageRepositoryImpl
import com.lovevery.messagemanager.addmessage.domain.AddMessageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
class AddMessageModule {

    @Provides
    fun provideAddMessageClient(retrofit: Retrofit): AddMessageClient {
        return retrofit.create(AddMessageClient::class.java)
    }

    @Provides
    fun provideAddMessageRepository(adsMessageRepositoryImpl: AddMessageRepositoryImpl): AddMessageRepository{
        return adsMessageRepositoryImpl
    }


}