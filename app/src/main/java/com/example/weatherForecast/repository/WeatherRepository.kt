package com.example.weatherForecast.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherForecast.local.ForecastItem
import com.example.weatherForecast.local.WeatherDao
import com.example.weatherForecast.local.WeatherEntity
import com.example.weatherForecast.network.WeatherService
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherService,
    private val dao: WeatherDao
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getWeather(city: String, apiKey: String): List<WeatherEntity> {
        return try {
            val response = api.getWeatherForecast(city, apiKey)
            val filteredForecasts = filterDailyForecasts(response.forecastList)

            val forecasts = filteredForecasts.map {
                val (date, time) = splitDateTime(it.dateTime)
                WeatherEntity(
                    city = city,
                    date = date,
                    time = time,
                    dateTime = it.dateTime,
                    temperature = it.main.temp,
                    description = it.weather[0].description,
                    icon = it.weather[0].icon
                )
            }

            dao.deleteForecasts(city)
            dao.insertAll(forecasts)
            forecasts
        } catch (e: Exception) {
            dao.getForecasts(city).ifEmpty { emptyList() }
        }
    }

    private fun splitDateTime(dateTime: String): Pair<String, String> {
        return try {
            val parts = dateTime.split(" ")
            if (parts.size == 2) {
                parts[0] to parts[1].substring(0, 5) // Take only HH:mm
            } else {
                dateTime to ""
            }
        } catch (e: Exception) {
            dateTime to ""
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterDailyForecasts(forecasts: List<ForecastItem>): List<ForecastItem> {
        // Keep your existing filtering logic, but now we'll keep all entries
        // for the selected days to show different times

        if (forecasts.isEmpty()) return emptyList()

        val currentDate = LocalDate.now()
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        return forecasts.filter { forecast ->
            val forecastDate = LocalDate.parse(forecast.dateTime.substring(0, 10))
            !forecastDate.isBefore(currentDate) &&
                    !forecastDate.isAfter(currentDate.plusDays(2))
        }
    }
}
