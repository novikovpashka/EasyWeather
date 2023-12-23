package com.example.easyweather.data.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class WeatherWithForecastDay(
    @Embedded val weather: Weather,
    @Relation(
        parentColumn = "cityId",
        entityColumn = "cityId"
    )
    val forecastDay: List<ForecastDay>
)