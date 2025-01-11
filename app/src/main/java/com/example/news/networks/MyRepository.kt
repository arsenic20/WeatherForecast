package com.example.news.networks

import com.example.news.model.NewsData
import com.example.news.roomDb.MyDao
import com.example.news.roomDb.SavedArticles
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.IOException
import javax.inject.Inject

class MyRepository @Inject constructor(private val dao: MyDao, private val apiService: ApiService) {

    private val _newsData = MutableStateFlow<NewsData?>(null)
    val newsData:StateFlow<NewsData?> get() = _newsData.asStateFlow()

    private val _entities = MutableStateFlow<List<SavedArticles>>(emptyList())
    val entities: StateFlow<List<SavedArticles>> get() = _entities.asStateFlow()

    suspend fun insert(entity: SavedArticles) {
        dao.insert(entity)
        refreshData()
    }

    suspend fun delete(entity: SavedArticles) {
        dao.delete(entity)
        refreshData()
    }

    suspend fun refreshData() {
        _entities.update { dao.getAll() }
    }

    suspend fun fetchNewsDataData() {
        try {
            val response = apiService.getNewsData()
            if (response.isSuccessful && response.body() != null) {
                _newsData.emit(response.body())
            } else {
                throw IOException("Failed to load news: ${response.message()}")
            }
        } catch (e: IOException) {
            _newsData.emit(null) // Emit null to signify no data
            throw e
        }
    }
}

