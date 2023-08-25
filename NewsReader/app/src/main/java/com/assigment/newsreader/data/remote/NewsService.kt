package com.assigment.newsreader.data.remote

import com.assigment.newsreader.BuildConfig
import com.assigment.newsreader.data.remote.dto.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("v2/top-headlines?country=us&apiKey=f5aaf75f0e7f42089961f60f9bc4d958")
    suspend fun fetchNews(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): NewsResponse

}