package com.example.easyweather.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ForecastDay(
    @PrimaryKey val dayId: Long,

    @ColumnInfo(name = "date_epoch") val dateEpoch: Long,
    @ColumnInfo(name = "maxtemp_c") val maxtempC: Double,
    @ColumnInfo(name = "mintemp_c") val mintempC: Double,
    @ColumnInfo(name = "avgtemp_c") val avgtempC: Double,
    @ColumnInfo(name = "maxwind_kph") val maxwindKph: Double,
    @ColumnInfo(name = "daily_chance_of_rain") val dailyChanceOfRain: Double,
    @ColumnInfo(name = "daily_chance_of_snow") val dailyChanceOfSnow: Double,
    @ColumnInfo(name = "condition_text") val conditionText: String,
    @ColumnInfo(name = "condition_icon") val conditionIcon: String,
    val sunrise: String,
    val sunset: String,

    val cityId: String
)
