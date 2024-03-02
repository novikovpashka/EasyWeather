package com.example.easyweather.data.network.models

import com.example.easyweather.data.local.entities.WeatherEntity
import com.google.gson.annotations.SerializedName


data class WeatherApiResponse(

    @SerializedName("location")
    val location: Location,

    @SerializedName("current")
    val current: Current,

    @SerializedName("forecast")
    val forecast: Forecast

)

fun WeatherApiResponse.asDatabaseModelWeather(): WeatherEntity {
    return WeatherEntity(
        cityId = this.getId(),
        city = location.name,
        country = location.country,
        latitude = location.latitude,
        longitude = location.longitude,
        lastUpdatedEpoch = current.lastUpdatedEpoch,
        tempC = current.tempC,
        conditionText = current.condition.text,
        conditionIcon = current.condition.icon,
        windKph = current.windKph,
        windDegree = current.windDegree,
        windDir = current.windDir,
        humidity = current.humidity,
        cloud = current.cloud,
        feelslikeC = current.feelslikeC
    )
}

fun WeatherApiResponse.currentLocationAsDatabaseModelWeather(): WeatherEntity {
    return WeatherEntity(
        cityId = this.getId(),
        city = location.name,
        country = location.country,
        latitude = location.latitude,
        longitude = location.longitude,
        lastUpdatedEpoch = current.lastUpdatedEpoch,
        tempC = current.tempC,
        conditionText = current.condition.text,
        conditionIcon = current.condition.icon,
        windKph = current.windKph,
        windDegree = current.windDegree,
        windDir = current.windDir,
        humidity = current.humidity,
        cloud = current.cloud,
        feelslikeC = current.feelslikeC
    )
}

fun WeatherApiResponse.getId(): String {
    return "${this.location.name},${this.location.country}"
}

const val CURRENT_LOCATION = "Current"



