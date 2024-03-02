package com.example.easyweather.data.model

data class WeatherWithForecast (
    val weather: Weather,
    val forecast: List<Forecast>
) {
    val id = weather.cityId
}

data class Weather(
    val cityId: String,
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val lastUpdatedEpoch: Int,
    val tempC: Double,
    val conditionText: String,
    val conditionIcon: String,
    val windKph: Double,
    val windDegree: Int,
    val windDir: String,
    val humidity: Int,
    val cloud: Int,
    val feelslikeC: Double,
)

data class Forecast (
    val dateEpoch: Int,
    val maxtempC: Double,
    val mintempC: Double,
    val avgtempC: Double,
    val maxwindKph: Double,
    val dailyChanceOfRain: Int,
    val dailyChanceOfSnow: Int,
    val conditionText: String,
    val conditionIcon: String,
    val sunrise: String,
    val sunset: String,
)