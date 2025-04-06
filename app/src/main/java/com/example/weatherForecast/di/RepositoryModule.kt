package com.example.weatherForecast.di

import com.example.weatherForecast.local.WeatherDao
import com.example.weatherForecast.network.WeatherService
import com.example.weatherForecast.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(
        api: WeatherService,
        dao: WeatherDao
    ): WeatherRepository {
        return WeatherRepository(api, dao)
    }
}
