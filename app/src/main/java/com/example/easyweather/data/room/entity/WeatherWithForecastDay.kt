package com.example.easyweather.data.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class WeatherWithForecastDay(
    @Embedded val currentWeather: CurrentWeather,
    @Relation(
        parentColumn = "cityId",
        entityColumn = "cityId"
    )
    val forecastDay: List<ForecastDay>
)
