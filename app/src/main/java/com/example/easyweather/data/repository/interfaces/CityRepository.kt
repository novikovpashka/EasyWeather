package com.example.easyweather.data.repository.interfaces

import com.example.easyweather.data.repository.CitySearchResponse
import kotlinx.coroutines.flow.Flow

interface CityRepository {
    fun searchCity (query: String): Flow<CitySearchResponse>
}