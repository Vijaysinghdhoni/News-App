package com.vijaydhoni.newsapp.domain.usecases

import com.vijaydhoni.newsapp.data.model.APIResponse
import com.vijaydhoni.newsapp.data.util.Resource
import com.vijaydhoni.newsapp.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(country: String, searchQuery: String, page: Int): Resource<APIResponse> {
        return newsRepository.getSearchedNews(country, searchQuery, page)
    }
}