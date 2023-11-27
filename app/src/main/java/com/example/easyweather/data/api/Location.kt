package com.example.easyweather.data.api

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("name")
    val name: String
)
