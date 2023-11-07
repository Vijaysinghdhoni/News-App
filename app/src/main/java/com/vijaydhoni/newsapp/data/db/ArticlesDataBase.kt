package com.vijaydhoni.newsapp.data.db


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vijaydhoni.newsapp.data.model.Article

@TypeConverters(Convertor::class)
@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class ArticlesDataBase : RoomDatabase() {

    abstract fun getArticlesDao(): ArticlesDao

}