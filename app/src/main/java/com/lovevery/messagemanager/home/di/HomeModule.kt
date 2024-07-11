package com.lovevery.messagemanager.home.di

import com.lovevery.messagemanager.home.data.HomeClient
import com.lovevery.messagemanager.home.data.HomeRepositoryImpl
import com.lovevery.messagemanager.home.domain.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
class HomeModule {
    @Provides
    fun provideHomeClient(retrofit: Retrofit): HomeClient {
        return retrofit.create(HomeClient::class.java)
    }

    @Provides
    fun provideHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository {
        return homeRepositoryImpl
    }
}