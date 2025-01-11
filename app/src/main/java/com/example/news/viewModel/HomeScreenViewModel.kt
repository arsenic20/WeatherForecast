package com.example.news.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.model.NewsData
import com.example.news.networks.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val myRepository: MyRepository) :
    ViewModel() {

    val newsData: StateFlow<NewsData?> get() = myRepository.newsData

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    init {
        fetchNewsData()
    }

    private fun fetchNewsData() {
        viewModelScope.launch {
            _isLoading.emit(true)
            _errorMessage.emit(null) // Reset error message
            try {
                myRepository.fetchNewsDataData()
            } catch (e: Exception) {
                _errorMessage.emit("Failed to fetch news: ${e.localizedMessage}")
            } finally {
                _isLoading.emit(false)
            }
        }
    }
}
