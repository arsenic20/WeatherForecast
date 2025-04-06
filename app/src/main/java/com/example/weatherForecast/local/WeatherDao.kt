package com.example.weatherForecast.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(weatherEntities: List<WeatherEntity>)

    @Query("SELECT * FROM weather_forecast WHERE city = :city ORDER BY dateTime ASC")
    suspend fun getForecasts(city: String): List<WeatherEntity>

    @Query("DELETE FROM weather_forecast WHERE city = :city")
    suspend fun deleteForecasts(city: String)
}