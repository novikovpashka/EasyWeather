package com.example.easyweather.data.repository

import com.example.easyweather.data.repository.CurrentWeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun loadWeatherForCurrentLocation(): Flow<CurrentWeatherResponse>
    fun loadWeatherForLocation(latLon: String): Flow<CurrentWeatherResponse>
    fun updateWeatherForSavedCities()
    suspend fun deleteSavedCity(id: String)

}