package com.example.easyweather.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.easyweather.ui.WeatherUiState

@Composable
fun MainWeatherForSelectedCity(
    weatherState: WeatherUiState,
    height: Dp,
    modifier: Modifier = Modifier,
    connection: MainWeatherNestedScrollConnection,
    listState: LazyListState,
    onMainWeatherScroll: (delta: Float) -> Unit
) {

    val scrollableState = rememberScrollableState { delta ->
        onMainWeatherScroll(delta)
        delta
    }

    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .scrollable(orientation = Orientation.Vertical, state = scrollableState)
//            .verticalScroll(state = scrollState)
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 24.dp,
                    bottomEnd = 24.dp
                )
            )
            .background(color = Color.Red.copy(alpha = 0.5f))
            .height(height + connection.mainWeatherOffset.pxToDp())
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "HELLO")
    }
}