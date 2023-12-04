package com.example.easyweather.data.api

import com.google.gson.annotations.SerializedName

data class ForecastDay(
    @SerializedName("date"       ) var date      : String?         = null,
    @SerializedName("date_epoch" ) var dateEpoch : Long?            = null,
    @SerializedName("day"        ) var day       : Day?            = Day(),
    @SerializedName("astro"      ) var astro     : Astro?          = Astro(),
    @SerializedName("hour"       ) var hour      : ArrayList<Hour> = arrayListOf()
)
