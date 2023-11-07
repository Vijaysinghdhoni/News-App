package com.vijaydhoni.newsapp.data.repository.dataSourceImpl

import com.vijaydhoni.newsapp.data.api.NewsAPiService
import com.vijaydhoni.newsapp.data.model.APIResponse
import com.vijaydhoni.newsapp.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsAPiService: NewsAPiService
) : NewsRemoteDataSource {
    override suspend fun getTopHeadlines(country: String, page: Int): Response<APIResponse> {
        return newsAPiService.getTopHeadlines(country, page)
    }

    override suspend fun getSearchedHeadlines(
        country: String,
        searchedQuery: String,
        page: Int
    ): Response<APIResponse> {
        return newsAPiService.getSearchedHeadlines(country, searchedQuery, page)
    }
}