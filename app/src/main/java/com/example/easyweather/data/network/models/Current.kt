package com.example.easyweather.data.network.models

import com.google.gson.annotations.SerializedName

data class Current(

    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Int,

    @SerializedName("temp_c")
    val tempC: Double,

    @SerializedName("condition")
    val condition: Condition,

    @SerializedName("wind_kph")
    val windKph: Double,

    @SerializedName("wind_degree")
    val windDegree: Int,

    @SerializedName("wind_dir")
    val windDir: String,

    @SerializedName("humidity")
    val humidity: Int,

    @SerializedName("cloud")
    val cloud: Int,

    @SerializedName("feelslike_c")
    val feelslikeC: Double

)
