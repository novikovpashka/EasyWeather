package com.example.easyweather.data.repository

import android.util.Log
import com.example.easyweather.data.local.dao.WeatherDao
import com.example.easyweather.data.local.entities.WeatherWithForecastEntity
import com.example.easyweather.data.local.entities.asExternalModel
import com.example.easyweather.data.location.LocationTracker
import com.example.easyweather.data.model.WeatherWithForecast
import com.example.easyweather.data.network.NoConnectivityException
import com.example.easyweather.data.network.WeatherApi
import com.example.easyweather.data.network.models.CURRENT_LOCATION
import com.example.easyweather.data.network.models.asDatabaseModelForecast
import com.example.easyweather.data.network.models.asDatabaseModelWeather
import com.example.easyweather.data.network.models.currentLocationAsDatabaseModelWeather
import com.example.easyweather.data.network.models.getId
import com.example.easyweather.data.repository.interfaces.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherDao: WeatherDao,
    private val weatherApi: WeatherApi,
    private val locationTracker: LocationTracker
) : WeatherRepository {

    val weather: Flow<List<WeatherWithForecast>> = weatherDao.getWeatherWithForecast().map {
        it.map(WeatherWithForecastEntity::asExternalModel)
    }
        .onStart { emit(emptyList()) }

    override fun loadWeatherForCurrentLocation() = flow {
        try {
            val currentLocation = locationTracker.getCurrentLocation()
            Log.v("mytag", currentLocation.toString())
            if (currentLocation != null) {
                val response =
                    weatherApi.getWeatherByCity(query = "${currentLocation.latitude},${currentLocation.longitude}")
                if (response.isSuccessful) {
                    response.body()
                        ?.let {
                            val x = weatherDao.insertWeather(it.currentLocationAsDatabaseModelWeather())
                            Log.v("mytag", x.toString())
                        }
                    response.body()?.let { weatherApiResponse ->
                        val cityId = CURRENT_LOCATION
                        weatherApiResponse.forecast.forecastday.forEach {
                            weatherDao.insertForecast(
                                it.asDatabaseModelForecast(
                                    cityId
                                )
                            )
                        }
                    }
                    emit(CurrentWeatherResponse.Success)
                } else {
                    emit(CurrentWeatherResponse.Error(response.errorBody().toString()))
                }
            } else emit(CurrentWeatherResponse.Error("Can't access to location. Try again later"))
        } catch (e: Exception) {
            when (e) {
                is NoConnectivityException -> emit(CurrentWeatherResponse.Error(e.message))
                else -> emit(CurrentWeatherResponse.Error("Something goes wrong. Try again later"))
            }
        }
    }
        .onStart {
            emit(CurrentWeatherResponse.Loading)
        }
        .conflate()
        .flowOn(Dispatchers.IO)

    override fun loadWeatherForLocation(latLon: String) = flow {
        val response =
            weatherApi.getWeatherByCity(query = latLon)
        if (response.isSuccessful) {
            response.body()
                ?.let {
                    val x = weatherDao.insertWeather(it.asDatabaseModelWeather())
                    Log.v("mytag", x.toString())
                }
            response.body()?.let { weatherApiResponse ->
                val cityId = weatherApiResponse.getId()
                weatherApiResponse.forecast.forecastday.forEach {
                    weatherDao.insertForecast(
                        it.asDatabaseModelForecast(
                            cityId
                        )
                    )
                }
            }
            emit(CurrentWeatherResponse.Success)
        } else {
            emit(CurrentWeatherResponse.Error(response.errorBody().toString()))
        }
    }
        .onStart {
            emit(CurrentWeatherResponse.Loading)
        }
        .conflate()
        .flowOn(Dispatchers.IO)

    override suspend fun deleteSavedCity(id: String) {
        weatherDao.deleteCity(id)
    }
}


sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}

sealed class CurrentWeatherResponse(val message: String? = null) {
    data object Success : CurrentWeatherResponse()
    class Error(message: String) : CurrentWeatherResponse(message)
    data object Loading : CurrentWeatherResponse()
}