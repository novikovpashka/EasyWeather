package com.example.easyweather.ui

import androidx.lifecycle.ViewModel
import com.example.easyweather.data.api.WeatherApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private var api: WeatherApi) : ViewModel() {

    private val _weather = MutableStateFlow("")
    val weather1: StateFlow<String> = _weather



    suspend fun getWeather () {
        api.getWeather(query = "London")
    }
}