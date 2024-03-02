package com.example.easyweather.data.network.models

import com.example.easyweather.data.model.CityExternalModel
import com.google.gson.annotations.SerializedName


data class City(

    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("region")
    var region: String,

    @SerializedName("country")
    var country: String,

    @SerializedName("lat")
    var lat: Double,

    @SerializedName("lon")
    var lon: Double,

    @SerializedName("url")
    var url: String

)


fun City.asExternalModel(): CityExternalModel {
    return CityExternalModel(
        id = id, city = name, country = country, latitude = lat, longitude = lon
    )
}




