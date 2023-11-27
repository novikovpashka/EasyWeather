package com.example.easyweather.data.api

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String
        get() = "No internet connection"
}