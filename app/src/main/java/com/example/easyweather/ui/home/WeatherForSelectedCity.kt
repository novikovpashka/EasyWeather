package com.example.easyweather.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.easyweather.ui.WeatherUiState

fun LazyListScope.weatherForSelectedCity(
    weatherState: WeatherUiState,
    modifier: Modifier = Modifier
) {

//    val isScrollingUp = state.isScrollingUp()

    item {
        Box(modifier = modifier
            .fillParentMaxWidth()
            .height(300.dp + 36.dp)
            .background(color = Color.Cyan))
    }

    item {
        Box(modifier = modifier
            .fillParentMaxWidth()
            .height(1000.dp)
            .background(color = Color.Magenta))
    }
}