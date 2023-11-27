package com.example.easyweather.data.api

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("temp_c")
    val tempCelsius: Double,

    @SerializedName("condition")
    val condition: Condition,

    @SerializedName("feelslike_c")
    val feelsLike: Double
)
