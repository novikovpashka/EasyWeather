package com.example.easyweather.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.easyweather.data.local.entities.ForecastEntity
import com.example.easyweather.data.local.entities.WeatherEntity
import com.example.easyweather.data.local.entities.WeatherWithForecastEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weatherEntity: WeatherEntity): Long

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertForecast(forecast: ForecastEntity)

    @Transaction
    @Query("SELECT * FROM WeatherEntity")
    fun getWeatherWithForecast() : Flow<List<WeatherWithForecastEntity>>


    @Query("DELETE FROM weatherentity WHERE cityId = :cityId")
    fun deleteCity(cityId: String)

}