package com.example.weatherForecast.network

import com.example.weatherForecast.local.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("q") city: String,
        @Query("appId") apiKey: String? = "d04c7693020bd4946eb38269f2a98879",
    ): WeatherResponse
}