package com.example.easyweather.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyweather.ui.WeatherUiState

fun LazyListScope.weatherForSelectedCity(
    weatherState: WeatherUiState,
    modifier: Modifier = Modifier,
    connection: MainWeatherNestedScrollConnection
) {

//    val isScrollingUp = state.isScrollingUp()
    
    item {
        Spacer(modifier = modifier.height(connection.listPadding.pxToDp()))
        LaunchedEffect(key1 = connection.listPadding) {
            Log.v("mytag", connection.listPadding.toString())
        }
    }

    item {
        Box(
            modifier = modifier
                .fillParentMaxWidth()
                .height(300.dp + 36.dp)
                .background(color = Color.Cyan)
            ,
            contentAlignment = Alignment.TopCenter
        ) {
            Text(text = "TEST", fontStyle = FontStyle.Normal, fontSize = 40.sp)
        }
    }

    item {
        Box(
            modifier = modifier
                .fillParentMaxWidth()
                .height(1000.dp)
                .background(color = Color.Magenta)
        )
    }
}