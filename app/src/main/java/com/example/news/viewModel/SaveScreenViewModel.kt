package com.example.news.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.networks.MyRepository
import com.example.news.roomDb.SavedArticles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveScreenViewModel @Inject constructor(private val repository: MyRepository) : ViewModel() {

    // Exposing the repository's StateFlow to the UI
    private val _savedBlogs = MutableStateFlow<List<SavedArticles>>(emptyList())
    val savedBlogs: StateFlow<List<SavedArticles>> get() = _savedBlogs.asStateFlow()

    init {
        refreshData()
    }

    fun addEntity(entity: SavedArticles) {
        viewModelScope.launch {
            repository.insert(entity)
            refreshData()
        }
    }

    fun deleteEntity(entity: SavedArticles) {
        viewModelScope.launch {
            repository.delete(entity)
            refreshData()
        }
    }

     fun refreshData() {
        viewModelScope.launch {
            repository.refreshData()
            _savedBlogs.emit(repository.entities.value)
        }
    }
}
