package com.example.easyweather.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.example.easyweather.data.model.WeatherWithForecast

data class WeatherWithForecastEntity(
    @Embedded val weather: WeatherEntity,
    @Relation(
        parentColumn = "cityId",
        entityColumn = "cityId"
    )
    val forecast: List<ForecastEntity>
)

fun WeatherWithForecastEntity.asExternalModel(): WeatherWithForecast {
    return WeatherWithForecast(
        this.weather.asExternalModel(),
        this.forecast.map {
            it.asExternalModel()
        }
    )
}