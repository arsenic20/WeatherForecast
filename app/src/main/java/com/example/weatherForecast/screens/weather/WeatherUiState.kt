package com.example.weatherForecast.screens.weather

import com.example.weatherForecast.local.WeatherEntity

sealed class WeatherUiState {
    object Empty : WeatherUiState()
    object Loading : WeatherUiState()
    data class Success(val forecasts: List<WeatherEntity>) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}
