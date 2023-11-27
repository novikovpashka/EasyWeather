package com.example.easyweather.data.api

import com.google.gson.annotations.SerializedName

data class DayWeather(
    @SerializedName("avgtemp_c")
    val averageTemp: Double,

    @SerializedName("condition")
    val condition: Condition
)
