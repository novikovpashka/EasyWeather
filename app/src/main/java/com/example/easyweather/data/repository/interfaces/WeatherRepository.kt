package com.example.easyweather.data.repository.interfaces

import com.example.easyweather.data.repository.CurrentWeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun loadWeatherForCurrentLocation(): Flow<CurrentWeatherResponse>
    fun loadWeatherForLocation(latLon: String): Flow<CurrentWeatherResponse>
    suspend fun deleteSavedCity(id: String)

}