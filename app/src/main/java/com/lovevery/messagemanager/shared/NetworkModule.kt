package com.lovevery.messagemanager.shared

import com.lovevery.messagemanager.home.data.HomeClient
import com.lovevery.messagemanager.home.data.HomeRepositoryImpl
import com.lovevery.messagemanager.home.domain.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitHelper(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://abraxvasbh.execute-api.us-east-2.amazonaws.com/proto/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    fun provideHomeClient(retrofit: Retrofit): HomeClient {
        return retrofit.create(HomeClient::class.java)
    }

    @Provides
    fun provideHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository {
        return homeRepositoryImpl
    }


}