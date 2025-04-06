package com.example.weatherForecast.local

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("list") val forecastList: List<ForecastItem>,
    @SerializedName("city") val city: CityInfo,
    @SerializedName("cod") val statusCode: String,
    @SerializedName("message") val message: String
)

data class ForecastItem(
    @SerializedName("dt_txt") val dateTime: String,
    @SerializedName("main") val main: MainWeather,
    @SerializedName("weather") val weather: List<WeatherDescription>
)

data class MainWeather(
    @SerializedName("temp") val temp: Double
)

data class WeatherDescription(
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)

data class CityInfo(
    @SerializedName("name") val name: String
)
