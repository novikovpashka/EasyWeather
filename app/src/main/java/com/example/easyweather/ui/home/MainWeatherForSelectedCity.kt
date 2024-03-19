package com.example.easyweather.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.easyweather.ui.WeatherUiState

@Composable
fun MainWeatherForSelectedCity(
    weatherState: WeatherUiState,
    height: Dp,
    modifier: Modifier = Modifier,
    connection: MainWeatherNestedScrollConnection
) {

    Box(
        modifier = modifier
            .offset { IntOffset(0, connection.mainWeatherOffset) }
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 24.dp,
                    bottomEnd = 24.dp
                )
            )
            .background(color = Color.Red)
            .height(height)
            .fillMaxWidth()


    )
}