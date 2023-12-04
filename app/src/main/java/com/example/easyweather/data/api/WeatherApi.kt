package com.example.easyweather.data.api

import com.example.easyweather.utils.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast.json")
    suspend fun getWeather(
        @Query("key") key: String = API_KEY,
        @Query("q") query: String,
        @Query("days") days: String = "3",
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): WeatherApiResponse

}