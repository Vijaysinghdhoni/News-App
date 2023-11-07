package com.vijaydhoni.newsapp.presentation.di

import com.vijaydhoni.newsapp.presentation.adapter.NewsHeadLineAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Singleton
    @Provides
    fun providesNewsHeadLineAdapter(): NewsHeadLineAdapter {
        return NewsHeadLineAdapter()
    }

}