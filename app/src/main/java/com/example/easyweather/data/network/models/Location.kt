package com.example.easyweather.data.network.models

import com.google.gson.annotations.SerializedName

data class Location(

    @SerializedName("name")
    val name: String,

    @SerializedName("region")
    val region: String,

    @SerializedName("country")
    val country: String,

    @SerializedName("lat")
    val latitude: Double,

    @SerializedName("lon")
    val longitude: Double

)
