package com.example.easyweather.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.easyweather.data.api.Forecast
import com.example.easyweather.data.room.entity.Weather
import com.example.easyweather.data.room.entity.WeatherWithForecastDay

@Dao
interface WeatherDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weather: Weather)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertForecast(forecast: Forecast)

    @Transaction
    @Query("SELECT * FROM Weather")
    fun getWeatherWithForecast() : List<WeatherWithForecastDay>

}