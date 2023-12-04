package com.example.easyweather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyweather.data.api.WeatherApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val api: WeatherApi) : ViewModel() {

    private val _weather = MutableStateFlow("Hello")
    val weather: StateFlow<String> = _weather


    init {
        _weather.value = "sosi"

        viewModelScope.launch(Dispatchers.IO) {

            val response = api.getWeather(query = "London")
            _weather.value = response.current!!.tempC.toString()

        }
    }


}