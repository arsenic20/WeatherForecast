package com.example.news.model

import com.example.news.roomDb.SavedArticles

data class NewsData(
    val status: String,
    val totalResults: Int,
    val articles: List<SavedArticles>
)
