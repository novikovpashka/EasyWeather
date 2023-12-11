package com.example.easyweather.data.api

import androidx.room.Entity

@Entity
data class ForecastDay(

    val dateEpoch: Long,
    val day: Day,
    val astro: Astro

)
