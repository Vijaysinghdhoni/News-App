package com.vijaydhoni.newsapp.data.repository.dataSource

import com.vijaydhoni.newsapp.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {


    suspend fun getTopHeadlines(country: String, page: Int): Response<APIResponse>

    suspend fun getSearchedHeadlines(country: String,searchedQuery:String, page: Int): Response<APIResponse>

}