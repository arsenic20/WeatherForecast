package com.example.news.utils

import android.util.Base64
import com.example.news.roomDb.SavedArticles
import com.google.gson.Gson

fun serializeArticleBase64(article: SavedArticles): String {
    val json = Gson().toJson(article)
    return Base64.encodeToString(json.toByteArray(Charsets.UTF_8), Base64.NO_WRAP)
}

fun deserializeArticleBase64(base64: String): SavedArticles {
    val json = String(Base64.decode(base64, Base64.NO_WRAP), Charsets.UTF_8)
    return Gson().fromJson(json, SavedArticles::class.java)
}