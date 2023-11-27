package com.example.easyweather.data.api

import com.google.gson.annotations.SerializedName

data class ForecastDay(
    @SerializedName("date_epoch")
    val date: Long,

    @SerializedName("date_epoch")
    val dayWeather: DayWeather
)
