package com.vijaydhoni.newsapp.presentation.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.vijaydhoni.newsapp.data.model.APIResponse
import com.vijaydhoni.newsapp.data.model.Article
import com.vijaydhoni.newsapp.data.util.Resource
import com.vijaydhoni.newsapp.domain.usecases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
    private val getNewsHeadLinesUsecase: GetNewsHeadLinesUsecase,
    private val app: Application,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
) : AndroidViewModel(app) {

    val newsHeadLines: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getNewsHeadLines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        newsHeadLines.postValue(Resource.Loading())

        try {
            if (isInternetAvailable(app)) {
                val apiResult = getNewsHeadLinesUsecase.execute(country, page)
                newsHeadLines.postValue(apiResult)
            } else {
                newsHeadLines.postValue(Resource.Error("Internet is not available "))
            }
        } catch (e: Exception) {
            newsHeadLines.postValue(Resource.Error(e.message.toString()))
        }

    }

    @Suppress("DEPRECATION")
    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }

    //searched news
    val searchedNews: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getSearchedNews(country: String, searchQuery: String, page: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            searchedNews.postValue(Resource.Loading())

            try {
                if (isInternetAvailable(app)) {
                    val apiResult = getSearchedNewsUseCase.execute(country, searchQuery, page)
                    searchedNews.postValue(apiResult)
                } else {
                    searchedNews.postValue(Resource.Error("Internet is not available "))
                }
            } catch (e: Exception) {
                searchedNews.postValue(Resource.Error(e.message.toString()))
            }
        }

    //saveNewsLocal

    fun saveArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        saveNewsUseCase.execute(article)
    }

    //getSavedNewsFromDb

    fun getSavedNewsFromDB() = liveData {
        getSavedNewsUseCase.execute().collect {
            emit(it)
        }
    }

    //deleteArticlesFromDB

    fun deleteSavedArticles(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        deleteSavedNewsUseCase.execute(article)
    }

}