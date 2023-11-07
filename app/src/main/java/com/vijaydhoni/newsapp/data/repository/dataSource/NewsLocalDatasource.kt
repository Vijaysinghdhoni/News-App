package com.vijaydhoni.newsapp.data.repository.dataSource

import com.vijaydhoni.newsapp.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDatasource {

    suspend fun saveArticleToDB(article: Article)

    fun getSavedArticlesFromDb(): Flow<List<Article>>

    suspend fun deleteArticle(article: Article)

}