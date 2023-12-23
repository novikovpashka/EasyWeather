package com.example.easyweather.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Weather(
    @PrimaryKey val cityId: String,

    val city: String,
    val country: String,
    @ColumnInfo(name = "last_updated") val lastUpdatedEpoch: Long,
    @ColumnInfo(name = "temp_c") val tempC: Double,
    @ColumnInfo(name = "condition_text") val conditionText: String,
    @ColumnInfo(name = "condition_icon") val conditionIcon: String,
    @ColumnInfo(name = "wind_kph") val windKph: Double,
    @ColumnInfo(name = "wind_degree") val windDegree: Double,
    @ColumnInfo(name = "wind_dir") val windDir: String,
    val humidity: Double,
    val cloud: Double,
    @ColumnInfo(name = "feelslike_c") val feelslikeC: Double,
    val forecast: List<ForecastDay>
)