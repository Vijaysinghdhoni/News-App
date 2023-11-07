package com.vijaydhoni.newsapp.presentation.di

import android.app.Application
import androidx.room.Room
import com.vijaydhoni.newsapp.data.db.ArticlesDao
import com.vijaydhoni.newsapp.data.db.ArticlesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun providesNewsDataBase(app: Application): ArticlesDataBase {
        return Room.databaseBuilder(app, ArticlesDataBase::class.java, "news_db")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Singleton
    @Provides
    fun providesNewsDao(articlesDataBase: ArticlesDataBase): ArticlesDao {
        return articlesDataBase.getArticlesDao()
    }

}