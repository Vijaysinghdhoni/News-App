package com.vijaydhoni.newsapp.presentation.di

import com.vijaydhoni.newsapp.data.api.NewsAPiService
import com.vijaydhoni.newsapp.data.repository.dataSource.NewsRemoteDataSource
import com.vijaydhoni.newsapp.data.repository.dataSourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun providesNewsRemoteDataSource(newsAPiService: NewsAPiService): NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(newsAPiService)
    }
}