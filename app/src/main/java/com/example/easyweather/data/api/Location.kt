package com.example.easyweather.data.api

import com.google.gson.annotations.SerializedName

data class Location(

    @SerializedName("name")
    val name: String,

    @SerializedName("region")
    val region: String,

    @SerializedName("country")
    val country: String

)
