package com.vijaydhoni.newsapp.data.db

import androidx.room.*
import com.vijaydhoni.newsapp.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)


    @Query("SELECT * FROM articles")
    fun getSavedArticle(): Flow<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)

}