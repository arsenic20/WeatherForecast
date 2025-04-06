package com.example.weatherForecast.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_forecast")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val city: String,
    val date: String,       // New field for date only
    val time: String,       // New field for time only
    val dateTime: String,   // Keep original for sorting
    val temperature: Double,
    val description: String,
    val icon: String
)