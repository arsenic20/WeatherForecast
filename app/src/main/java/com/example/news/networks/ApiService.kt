package com.example.news.networks

import com.example.news.model.NewsData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/api/getArticleList") // Replace with the actual endpoint
    suspend fun getNewsData(): Response<NewsData>
}
