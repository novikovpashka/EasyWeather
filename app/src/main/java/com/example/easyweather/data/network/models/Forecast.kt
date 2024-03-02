package com.example.easyweather.data.network.models

import com.example.easyweather.data.local.entities.ForecastEntity
import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("forecastday")
    val forecastday: ArrayList<ForecastDay>
)

data class ForecastDay(
    @SerializedName("date_epoch")
    val dateEpoch: Int,
    val day: Day,
    val astro: Astro
)

data class Day(

    @SerializedName("maxtemp_c")
    val maxtempC: Double,

    @SerializedName("mintemp_c")
    val mintempC: Double,

    @SerializedName("avgtemp_c")
    val avgtempC: Double,

    @SerializedName("maxwind_kph")
    val maxwindKph: Double,

    @SerializedName("daily_chance_of_rain")
    val dailyChanceOfRain: Int,

    @SerializedName("daily_chance_of_snow")
    val dailyChanceOfSnow: Int,

    @SerializedName("condition")
    val condition: Condition

)

data class Condition(

    @SerializedName("text")
    val text: String,

    @SerializedName("icon")
    val icon: String

)

data class Astro(

    @SerializedName("sunrise")
    val sunrise: String,

    @SerializedName("sunset")
    val sunset: String

)

fun ForecastDay.asDatabaseModelForecast(cityId: String): ForecastEntity {
    return ForecastEntity(
        dayId = "$cityId ${this.dateEpoch}",
        dateEpoch = this.dateEpoch,
        maxtempC = this.day.maxtempC,
        mintempC = this.day.mintempC,
        avgtempC = this.day.avgtempC,
        maxwindKph = this.day.maxwindKph,
        dailyChanceOfRain = this.day.dailyChanceOfRain,
        dailyChanceOfSnow = this.day.dailyChanceOfSnow,
        conditionText = this.day.condition.text,
        conditionIcon = this.day.condition.icon,
        sunrise = this.astro.sunrise,
        sunset = this.astro.sunset,
        cityId = cityId
    )
}
