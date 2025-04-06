package com.example.weatherForecast.ui.theme.weather


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherForecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Empty)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchWeather() {
        if (_searchQuery.value.isBlank()) {
            _uiState.value = WeatherUiState.Error("Please enter a city name")
            return
        }

        _uiState.value = WeatherUiState.Loading

        viewModelScope.launch {
            try {
                val forecasts = repository.getWeather(_searchQuery.value, "d04c7693020bd4946eb38269f2a98879")
                if (forecasts.isEmpty()) {
                    _uiState.value = WeatherUiState.Error("No weather data found")
                } else {
                    _uiState.value = WeatherUiState.Success(forecasts)
                }
            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error(
                    e.message ?: "Failed to fetch weather data"
                )
            }
        }
    }
}


