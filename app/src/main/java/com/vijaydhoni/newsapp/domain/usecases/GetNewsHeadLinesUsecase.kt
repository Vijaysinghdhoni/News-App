package com.vijaydhoni.newsapp.domain.usecases

import com.vijaydhoni.newsapp.data.model.APIResponse
import com.vijaydhoni.newsapp.data.util.Resource
import com.vijaydhoni.newsapp.domain.repository.NewsRepository

class GetNewsHeadLinesUsecase(private val newsRepository: NewsRepository) {

    suspend fun execute(country: String, page: Int): Resource<APIResponse> {
        return newsRepository.getNewsHeadLines(country, page)
    }
}