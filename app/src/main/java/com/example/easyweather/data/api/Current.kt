package com.example.easyweather.data.api

import com.google.gson.annotations.SerializedName

data class Current(

    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Long,

    @SerializedName("temp_c")
    val tempC: Double,

    @SerializedName("condition")
    val condition: Condition,

    @SerializedName("wind_kph")
    val windKph: Double,

    @SerializedName("wind_degree")
    val windDegree: Double,

    @SerializedName("wind_dir")
    val windDir: String,

    @SerializedName("humidity")
    val humidity: Double,

    @SerializedName("cloud")
    val cloud: Double,

    @SerializedName("feelslike_c")
    val feelslikeC: Double

)
