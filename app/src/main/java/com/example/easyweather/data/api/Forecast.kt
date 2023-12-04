package com.example.easyweather.data.api

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("forecastday" ) var forecastday : ArrayList<ForecastDay> = arrayListOf()
)
