package com.example.easyweather.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.easyweather.data.model.Weather

@Entity
data class WeatherEntity(
    @PrimaryKey val cityId: String,

    val city: String,

    val country: String,

    val latitude: Double,

    val longitude: Double,

    @ColumnInfo(name = "last_updated")
    val lastUpdatedEpoch: Int,

    @ColumnInfo(name = "temp_c")
    val tempC: Double,

    @ColumnInfo(name = "condition_text")
    val conditionText: String,

    @ColumnInfo(name = "condition_icon")
    val conditionIcon: String,

    @ColumnInfo(name = "wind_kph")
    val windKph: Double,

    @ColumnInfo(name = "wind_degree")
    val windDegree: Int,

    @ColumnInfo(name = "wind_dir")
    val windDir: String,

    val humidity: Int,

    val cloud: Int,

    @ColumnInfo(name = "feelslike_c")
    val feelslikeC: Double
)

fun WeatherEntity.asExternalModel(): Weather {
    return Weather(
        cityId,
        city,
        country,
        latitude,
        longitude,
        lastUpdatedEpoch,
        tempC,
        conditionText,
        conditionIcon,
        windKph,
        windDegree,
        windDir,
        humidity,
        cloud,
        feelslikeC
    )
}