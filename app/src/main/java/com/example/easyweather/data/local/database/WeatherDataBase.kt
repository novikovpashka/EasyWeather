package com.example.easyweather.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.easyweather.data.local.dao.WeatherDao
import com.example.easyweather.data.local.entities.ForecastEntity
import com.example.easyweather.data.local.entities.WeatherEntity

@Database (version = 1, entities = [WeatherEntity::class, ForecastEntity::class])
abstract class WeatherDataBase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}