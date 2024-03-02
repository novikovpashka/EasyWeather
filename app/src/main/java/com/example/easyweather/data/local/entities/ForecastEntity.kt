package com.example.easyweather.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.easyweather.data.model.Forecast

@Entity
data class ForecastEntity(
    @PrimaryKey val dayId: String,

    @ColumnInfo(name = "date_epoch")
    val dateEpoch: Int,

    @ColumnInfo(name = "maxtemp_c")
    val maxtempC: Double,

    @ColumnInfo(name = "mintemp_c")
    val mintempC: Double,

    @ColumnInfo(name = "avgtemp_c")
    val avgtempC: Double,

    @ColumnInfo(name = "maxwind_kph")
    val maxwindKph: Double,

    @ColumnInfo(name = "daily_chance_of_rain")
    val dailyChanceOfRain: Int,

    @ColumnInfo(name = "daily_chance_of_snow")
    val dailyChanceOfSnow: Int,

    @ColumnInfo(name = "condition_text")
    val conditionText: String,

    @ColumnInfo(name = "condition_icon")
    val conditionIcon: String,

    val sunrise: String,

    val sunset: String,

    //City id linked to
    val cityId: String
)

fun ForecastEntity.asExternalModel(): Forecast {
    return Forecast(
        dateEpoch,
        maxtempC,
        mintempC,
        avgtempC,
        maxwindKph,
        dailyChanceOfRain,
        dailyChanceOfSnow,
        conditionText,
        conditionIcon,
        sunrise,
        sunset
    )
}
