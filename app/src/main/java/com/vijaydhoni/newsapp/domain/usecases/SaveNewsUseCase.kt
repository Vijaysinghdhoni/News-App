package com.vijaydhoni.newsapp.domain.usecases

import com.vijaydhoni.newsapp.data.model.Article
import com.vijaydhoni.newsapp.domain.repository.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article) = newsRepository.saveNews(article)
}