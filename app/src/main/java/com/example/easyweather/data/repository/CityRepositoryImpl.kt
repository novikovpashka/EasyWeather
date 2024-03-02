package com.example.easyweather.data.repository

import com.example.easyweather.data.model.CityExternalModel
import com.example.easyweather.data.network.NoConnectivityException
import com.example.easyweather.data.network.WeatherApi
import com.example.easyweather.data.network.models.asExternalModel
import com.example.easyweather.data.repository.interfaces.CityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(private val weatherApi: WeatherApi) : CityRepository {
    override fun searchCity(query: String) = flow {
        try {
            val cities = weatherApi.searchCityByQuery(query = query)
            if (cities.isSuccessful) {
                cities.body()?.let {
                    emit(CitySearchResponse.Success(it.toMutableList().map { city ->
                        city.asExternalModel()
                    }))
                }
            } else {
                emit(CitySearchResponse.Error(cities.errorBody().toString()))
            }
        } catch (e: Exception) {
            when (e) {
                is NoConnectivityException -> emit(CitySearchResponse.Error(e.message))
                else -> emit(CitySearchResponse.Error(e.message!!))
//                else -> emit(CitySearchResponse.Error("Something goes wrong. Try again later"))
            }
        }
    }
        .onStart { emit(CitySearchResponse.Loading) }
        .flowOn(Dispatchers.IO)
}

sealed class CitySearchResponse(
    val cities: List<CityExternalModel>? = null,
    val message: String? = null
) {
    class Success(cities: List<CityExternalModel>) : CitySearchResponse(cities = cities)
    class Error(message: String) : CitySearchResponse(message = message)
    data object Loading : CitySearchResponse()
}