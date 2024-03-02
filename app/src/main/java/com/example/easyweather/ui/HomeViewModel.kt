package com.example.easyweather.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyweather.data.model.CityExternalModel
import com.example.easyweather.data.model.WeatherWithForecast
import com.example.easyweather.data.network.models.WeatherApiResponse
import com.example.easyweather.data.repository.CityRepositoryImpl
import com.example.easyweather.data.repository.CitySearchResponse
import com.example.easyweather.data.repository.CurrentWeatherResponse
import com.example.easyweather.data.repository.UserPreferencesRepository
import com.example.easyweather.data.repository.WeatherRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepositoryImpl: WeatherRepositoryImpl,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val cityRepositoryImpl: CityRepositoryImpl
) : ViewModel() {

    private val _weather = MutableStateFlow("Hello")
    val weather: StateFlow<String> = _weather

    private val _state = MutableStateFlow("nothing")
    val state: StateFlow<String> = _state

    private val _locationPermissionFirstTimeRequest = MutableStateFlow(false)
    val locationPermissionFirstTimeRequested: StateFlow<Boolean> =
        _locationPermissionFirstTimeRequest

    private val _searchedCities: MutableStateFlow<List<CityExternalModel>> =
        MutableStateFlow(emptyList())
    val searchedCities: StateFlow<List<CityExternalModel>> = _searchedCities

    private val searchJobQuery = mutableListOf<Job>()

    private val _savedCitiesWeather: MutableStateFlow<List<WeatherWithForecast>> =
        MutableStateFlow(emptyList())
    val savedCitiesWeather: StateFlow<List<WeatherWithForecast>> = _savedCitiesWeather



    init {
        viewModelScope.launch {
            weatherRepositoryImpl.weather.filterNotNull().collect {
                if (it.isNotEmpty()) {
                    _weather.value = it[0].weather.city
                }
                _savedCitiesWeather.value = it
            }
        }
        viewModelScope.launch {
            userPreferencesRepository.userPreferencesFlow.collect {
                _locationPermissionFirstTimeRequest.value =
                    it.locationPermissionFirstTimeRequest
            }
        }
    }

    fun searchCity(query: String) {
        if (searchJobQuery.isNotEmpty()) {
            searchJobQuery.forEach {
                it.cancel()
            }
            searchJobQuery.clear()
        }
        val job = viewModelScope.launch {
            cityRepositoryImpl.searchCity(query).filterNotNull()
                .collectLatest {
                    when (it) {
                        is CitySearchResponse.Loading -> {
                            Log.v("mytag", "search loading")
                        }

                        is CitySearchResponse.Success -> {
                            Log.v("mytag", "search success")

                            it.cities?.let { cities ->
                                _searchedCities.value = cities
                            }
                        }

                        is CitySearchResponse.Error -> {
                            it.message.let { message ->
                                if (message != null) {
                                    Log.v("mytag", message)
                                }
                            }

                        }
                    }
                }
        }
        searchJobQuery.add(job)
    }

    fun saveCity(latLon: String) = viewModelScope.launch{
        weatherRepositoryImpl.loadWeatherForLocation(latLon).filterNotNull().collect {
        }
    }

    fun refreshCurrentCity() = viewModelScope.launch {
        weatherRepositoryImpl.loadWeatherForCurrentLocation().filterNotNull().collect {
            when (it) {
                is CurrentWeatherResponse.Success -> _state.value = "Success"
                is CurrentWeatherResponse.Error -> _state.value = it.message.toString()
                is CurrentWeatherResponse.Loading -> _state.value = "Loading"
            }
        }
    }

    fun deleteSavedCity (id: String) = viewModelScope.launch (Dispatchers.IO) {
        weatherRepositoryImpl.deleteSavedCity(id)
    }

    fun setLocationPermissionFirstTimeRequest(locationPermissionFirstTimeRequest: Boolean) =
        viewModelScope.launch {
            userPreferencesRepository.setLocationPermissionPermanentlyDeclined(
                locationPermissionFirstTimeRequest
            )
        }

}

sealed class WeatherUiState {
    data object Empty : WeatherUiState()
    data class Success(val weatherApiResponse: WeatherApiResponse) : WeatherUiState()
    data object Loading : WeatherUiState()
    data class Error(val error: String) : WeatherUiState()
}


