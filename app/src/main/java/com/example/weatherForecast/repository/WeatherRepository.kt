package com.example.weatherForecast.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherForecast.local.ForecastItem
import com.example.weatherForecast.local.WeatherDao
import com.example.weatherForecast.local.WeatherEntity
import com.example.weatherForecast.network.WeatherService
import java.time.LocalDate
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherService,
    private val dao: WeatherDao
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getWeather(city: String, apiKey: String): Result<List<WeatherEntity>> {
        return try {
            val response = api.getWeatherForecast(city, apiKey)

            when {
                !response.isSuccessful -> {
                    val errorBody = response.errorBody()?.string()

                    val errorMessage = when (response.code()) {
                        404 -> "City not found"
                        401 -> "Invalid API key"
                        429 -> "Too many requests"
                        500 -> "Server error"
                        else -> "Error ${response.code()}: ${errorBody ?: "Unknown error"}"
                    }

                    // Check if we have cached data
                    val cachedData = dao.getForecasts(city)
                    if (cachedData.isNotEmpty()) {
                        Result.success(cachedData)
                    } else {
                        Result.failure(Exception(errorMessage))
                    }
                }

                else -> {
                    // Success case
                    val forecasts = response.body()?.forecastList?.let { forecastList ->
                        filterDailyForecasts(forecastList).map {
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
                    } ?: emptyList()

                    dao.deleteForecasts(city)
                    dao.insertAll(forecasts)
                    Result.success(forecasts)
                }
            }
        } catch (e: Exception) {
            // Only for actual network failures (not API errors)
            val cachedData = dao.getForecasts(city)
            if (cachedData.isNotEmpty()) {
                Result.success(cachedData)
            } else {
                Result.failure(e)
            }
        }
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
    //Filtered 3Day ForeCast Data from current day

    if (forecasts.isEmpty()) return emptyList()

    val currentDate = LocalDate.now()

    return forecasts.filter { forecast ->
        val forecastDate = LocalDate.parse(forecast.dateTime.substring(0, 10))
        !forecastDate.isBefore(currentDate) &&
                !forecastDate.isAfter(currentDate.plusDays(2))
    }
}
