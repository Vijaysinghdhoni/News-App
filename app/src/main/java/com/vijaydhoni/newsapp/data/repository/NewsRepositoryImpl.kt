package com.vijaydhoni.newsapp.data.repository

import com.vijaydhoni.newsapp.data.model.APIResponse
import com.vijaydhoni.newsapp.data.model.Article
import com.vijaydhoni.newsapp.data.repository.dataSource.NewsLocalDatasource
import com.vijaydhoni.newsapp.data.repository.dataSource.NewsRemoteDataSource
import com.vijaydhoni.newsapp.data.util.Resource
import com.vijaydhoni.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDatasource: NewsLocalDatasource
) : NewsRepository {
    override suspend fun getNewsHeadLines(country: String, page: Int): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadlines(country, page))
    }

    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Resource<APIResponse> {
        return responseToResource(
            newsRemoteDataSource.getSearchedHeadlines(
                country,
                searchQuery,
                page
            )
        )
    }

    private fun responseToResource(response: Response<APIResponse>): Resource<APIResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }


    override suspend fun saveNews(article: Article) {
        newsLocalDatasource.saveArticleToDB(article)
    }

    override suspend fun deleteNews(article: Article) {
        newsLocalDatasource.deleteArticle(article)
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDatasource.getSavedArticlesFromDb()
    }
}