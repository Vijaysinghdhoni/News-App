package com.vijaydhoni.newsapp.domain.usecases

import com.vijaydhoni.newsapp.data.model.Article
import com.vijaydhoni.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {

    fun execute(): Flow<List<Article>> {
        return newsRepository.getSavedNews()
    }
}