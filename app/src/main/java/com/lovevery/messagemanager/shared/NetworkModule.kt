package com.lovevery.messagemanager.shared

import com.google.gson.Gson
import com.lovevery.messagemanager.home.data.HomeClient
import com.lovevery.messagemanager.home.data.HomeRepositoryImpl
import com.lovevery.messagemanager.home.domain.HomeRepository
import com.lovevery.messagemanager.profile.data.ProfileClient
import com.lovevery.messagemanager.profile.data.ProfileRepositoryImpl
import com.lovevery.messagemanager.profile.domain.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitHelper(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl("https://abraxvasbh.execute-api.us-east-2.amazonaws.com/proto/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }


    @Provides
    fun provideHomeClient(retrofit: Retrofit): HomeClient {
        return retrofit.create(HomeClient::class.java)
    }

    @Provides
    fun provideHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository {
        return homeRepositoryImpl
    }

    @Provides
    fun provideProfileClient(retrofit: Retrofit): ProfileClient {
        return retrofit.create(ProfileClient::class.java)
    }

    @Provides
    fun provideProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository {
        return profileRepositoryImpl
    }


}