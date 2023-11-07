package com.vijaydhoni.newsapp.presentation.di

import com.vijaydhoni.newsapp.BuildConfig
import com.vijaydhoni.newsapp.data.api.NewsAPiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetModule {


    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {

        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesNewsApiService(retrofit: Retrofit): NewsAPiService {
        return retrofit.create(NewsAPiService::class.java)
    }

}