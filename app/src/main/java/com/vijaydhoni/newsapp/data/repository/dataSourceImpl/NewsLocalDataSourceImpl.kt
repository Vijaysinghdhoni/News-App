package com.vijaydhoni.newsapp.data.repository.dataSourceImpl

import com.vijaydhoni.newsapp.data.db.ArticlesDao
import com.vijaydhoni.newsapp.data.model.Article
import com.vijaydhoni.newsapp.data.repository.dataSource.NewsLocalDatasource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(
    private val articlesDao: ArticlesDao
) : NewsLocalDatasource {
    override suspend fun saveArticleToDB(article: Article) {
        articlesDao.insertArticle(article)
    }

    override fun getSavedArticlesFromDb(): Flow<List<Article>> {
        return articlesDao.getSavedArticle()
    }

    override suspend fun deleteArticle(article: Article) {
        articlesDao.deleteArticle(article)
    }
}