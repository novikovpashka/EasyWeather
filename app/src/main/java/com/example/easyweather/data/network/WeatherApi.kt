package com.example.easyweather.data.network

import com.example.easyweather.data.network.models.City
//import com.example.easyweather.data.network.models.CitySearchApiResponse
import com.example.easyweather.data.network.models.WeatherApiResponse
import com.example.easyweather.utils.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast.json")
    suspend fun getWeatherByCity(
        @Query("key") key: String = API_KEY,
        @Query("q") query: String,
        @Query("days") days: String = "3",
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): Response<WeatherApiResponse>

    @GET("search.json")
    suspend fun searchCityByQuery(
        @Query("key") key: String = API_KEY,
        @Query("q") query: String
    ): Response<ArrayList<City>>

}