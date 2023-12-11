package com.example.easyweather.data.api

import com.google.gson.annotations.SerializedName


data class WeatherApiResponse(

    @SerializedName("location")
    val location: Location,

    @SerializedName("current")
    val current: Current,

    @SerializedName("forecast")
    val forecast: Forecast

)