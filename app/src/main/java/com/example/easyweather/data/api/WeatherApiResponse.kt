package com.example.easyweather.data.api

import com.google.gson.annotations.SerializedName


data class WeatherApiResponse(

    @SerializedName("location")
    var loaction: Location,

    @SerializedName("current")
    var currentWeather: CurrentWeather,

    @SerializedName("forecast")
    var forecast: List<Forecast>
)