package com.example.easyweather.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FilledTonalButton

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.easyweather.ui.HomeViewModel


@Composable
fun Home(onDayClick: (Long) -> Unit, viewmodel: HomeViewModel = hiltViewModel()) {
    val currentWeather by viewmodel.weather.collectAsState()
    Surface {
        Box(
            modifier = Modifier
                .background(color = Color.Cyan)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {

                Text(text = currentWeather)


                FilledTonalButton(onClick = {
                    onDayClick(1)
                }) {

                }

            }
        }
    }
}