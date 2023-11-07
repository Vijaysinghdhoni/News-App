package com.vijaydhoni.newsapp.presentation.di

import com.vijaydhoni.newsapp.data.db.ArticlesDao
import com.vijaydhoni.newsapp.data.repository.dataSource.NewsLocalDatasource
import com.vijaydhoni.newsapp.data.repository.dataSourceImpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun providesLocalDataSource(articlesDao: ArticlesDao): NewsLocalDatasource {
        return NewsLocalDataSourceImpl(articlesDao)
    }

}