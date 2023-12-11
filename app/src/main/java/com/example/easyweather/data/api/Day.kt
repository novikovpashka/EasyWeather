package com.example.easyweather.data.api

import com.google.gson.annotations.SerializedName

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
    val dailyChanceOfRain: Double,

    @SerializedName("daily_chance_of_snow")
    val dailyChanceOfSnow: Double,

    @SerializedName("condition")
    val condition: Condition

)
