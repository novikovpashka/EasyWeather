package com.example.easyweather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyweather.data.model.CityExternalModel
import com.example.easyweather.data.model.WeatherWithForecast
import com.example.easyweather.data.repository.CityRepositoryImpl
import com.example.easyweather.data.repository.CitySearchResponse
import com.example.easyweather.data.repository.CurrentWeatherResponse
import com.example.easyweather.data.repository.UserPreferencesRepository
import com.example.easyweather.data.repository.WeatherRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepositoryImpl: WeatherRepositoryImpl,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val cityRepositoryImpl: CityRepositoryImpl
) : ViewModel() {

    val weather: StateFlow<WeatherUiState> = weatherRepositoryImpl.weather.map {
        WeatherUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = WeatherUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )

    private val _weatherUiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val weatherUiState: StateFlow<WeatherUiState> = _weatherUiState

    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
    val searchUiState: StateFlow<SearchUiState> = _searchUiState

    private val _locationPermissionFirstTimeRequest = MutableStateFlow(false)
    val locationPermissionFirstTimeRequested: StateFlow<Boolean> =
        _locationPermissionFirstTimeRequest

    private val _searchedCities: MutableStateFlow<List<CityExternalModel>> =
        MutableStateFlow(emptyList())
    val searchedCities: StateFlow<List<CityExternalModel>> = _searchedCities

    private val searchJobQuery = mutableListOf<Job>()

    init {
        isLocationPermissionFirstTimeRequested()
        loadWeather()
    }

    private fun isLocationPermissionFirstTimeRequested() {
        viewModelScope.launch {
            userPreferencesRepository.userDataFlow.collect {
                _locationPermissionFirstTimeRequest.value =
                    it.locationPermissionBeenRequestedOnce
            }
        }
    }

    private fun loadWeather() {
        viewModelScope.launch {
            weatherRepositoryImpl.weather.filterNotNull().collect {
                if (it.isNotEmpty()) {
                    _weatherUiState.value = WeatherUiState.Success(it)
                }
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
            cityRepositoryImpl.searchCity(query.trim()).filterNotNull().conflate()
                .onEach { delay(200) }
                .collectLatest { response ->
                    when (response) {
                        is CitySearchResponse.Loading -> {
                            delay(200)
                            _searchUiState.value = SearchUiState.Loading
                        }

                        is CitySearchResponse.Success -> {
                            _searchedCities.value = response.cities
                            _searchUiState.value = SearchUiState.Success(response.cities)
                        }

                        is CitySearchResponse.Error -> {
                            _searchUiState.value = SearchUiState.Error(response.message)
                        }
                    }
                }
        }
        searchJobQuery.add(job)
    }

    fun saveCity(latLon: String) = viewModelScope.launch {
        weatherRepositoryImpl.loadWeatherForLocation(latLon).filterNotNull().collect {
        }
    }

    fun refreshCurrentLocationCity() = viewModelScope.launch {
        weatherRepositoryImpl.loadWeatherForCurrentLocation().filterNotNull().collect {
            when (it) {
                is CurrentWeatherResponse.Success -> Unit
                is CurrentWeatherResponse.Error -> _weatherUiState.value =
                    WeatherUiState.Error(it.message.toString())

                is CurrentWeatherResponse.Loading -> _weatherUiState.value = WeatherUiState.Loading
            }
        }
    }

    fun deleteSavedCity(id: String) = viewModelScope.launch(Dispatchers.IO) {
        weatherRepositoryImpl.deleteSavedCity(id)
    }

    fun setLocationPermissionBeenRequestedOnce() =
        viewModelScope.launch {
            userPreferencesRepository.setLocationPermissionBeenRequestedOnce()
        }
}

sealed class WeatherUiState {
    data class Success (val weather: List<WeatherWithForecast>) : WeatherUiState()
    data object Loading : WeatherUiState()
    data class Error(val error: String) : WeatherUiState()
}

sealed class SearchUiState {
    data class Success(val searchedCities: List<CityExternalModel>) : SearchUiState()
    data object Loading : SearchUiState()
    data class Error(val error: String) : SearchUiState()
}


