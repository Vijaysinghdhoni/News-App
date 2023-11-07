package com.vijaydhoni.newsapp.presentation.di

import com.vijaydhoni.newsapp.domain.repository.NewsRepository
import com.vijaydhoni.newsapp.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun providesNewsHealineUseCase(
        newsRepository: NewsRepository
    ): GetNewsHeadLinesUsecase {
        return GetNewsHeadLinesUsecase(newsRepository)
    }

    @Singleton
    @Provides
    fun providesSearchedNewsUseCase(
        newsRepository: NewsRepository
    ): GetSearchedNewsUseCase {
        return GetSearchedNewsUseCase(newsRepository)
    }


    @Singleton
    @Provides
    fun providesSaveNewsUseCase(
        newsRepository: NewsRepository
    ): SaveNewsUseCase {
        return SaveNewsUseCase(newsRepository)
    }


    @Singleton
    @Provides
    fun providesGetSavedNewsUseCase(
        newsRepository: NewsRepository
    ): GetSavedNewsUseCase {
        return GetSavedNewsUseCase(newsRepository)
    }


    @Singleton
    @Provides
    fun providesDeleteSavedNewsUseCase(
        newsRepository: NewsRepository
    ): DeleteSavedNewsUseCase {
        return DeleteSavedNewsUseCase(newsRepository)
    }


}