package com.example.easyweather.data.repository

import com.example.easyweather.data.model.CityExternalModel
import com.example.easyweather.data.network.NoConnectivityException
import com.example.easyweather.data.network.WeatherApi
import com.example.easyweather.data.network.models.asExternalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(private val weatherApi: WeatherApi) : CityRepository {
    override fun searchCity(query: String) = flow {
        try {
            if (query.length < 3) {
                emit(CitySearchResponse.Success(emptyList()))
                return@flow
            }
            val cities = weatherApi.searchCityByQuery(query = query)

            if (!cities.isSuccessful) {
                emit(CitySearchResponse.Error(cities.errorBody().toString()))
                return@flow
            }

            cities.body()?.let { citiesList ->
                emit(CitySearchResponse.Success(citiesList.toMutableList().map { city ->
                    city.asExternalModel()
                }.sortedBy {
                    it.city
                }))
            }

        } catch (e: Exception) {
            when (e) {
                is NoConnectivityException -> emit(CitySearchResponse.Error(e.message))
                else -> emit(CitySearchResponse.Error(e.message!!))
            }
        }
    }
        .onStart { emit(CitySearchResponse.Loading) }
        .flowOn(Dispatchers.IO)
}

sealed class CitySearchResponse(
) {
    data class Success(val cities: List<CityExternalModel>) : CitySearchResponse()
    data class Error(val message: String) : CitySearchResponse()
    data object Loading : CitySearchResponse()
}