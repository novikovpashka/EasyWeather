package com.example.easyweather.data.model

data class CityExternalModel(
    val id: Int,
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)

fun CityExternalModel.getCityAndCountry(): String {
    return "${this.city},${this.country}"
}
