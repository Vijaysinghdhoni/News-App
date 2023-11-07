package com.vijaydhoni.newsapp.presentation.di

import com.vijaydhoni.newsapp.data.repository.NewsRepositoryImpl
import com.vijaydhoni.newsapp.data.repository.dataSource.NewsLocalDatasource
import com.vijaydhoni.newsapp.data.repository.dataSource.NewsRemoteDataSource
import com.vijaydhoni.newsapp.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        newsLocalDatasource: NewsLocalDatasource
    ): NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource, newsLocalDatasource)
    }
}