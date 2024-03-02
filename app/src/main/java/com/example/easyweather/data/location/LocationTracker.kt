package com.example.easyweather.data.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}