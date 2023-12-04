package com.example.easyweather.data.api

import com.google.gson.annotations.SerializedName


data class WeatherApiResponse(

    @SerializedName("location" ) var location : Location? = Location(),
    @SerializedName("current"  ) var current  : Current?  = Current(),
    @SerializedName("forecast" ) var forecast : Forecast? = Forecast()

)