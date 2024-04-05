package com.example.easyweather.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyweather.ui.WeatherUiState

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.weatherForSelectedCity(
    weatherState: WeatherUiState,
    modifier: Modifier,

    maxHeight: Dp,
    maxHeightPx: Int,
    minHeight: Dp,
    minHeightPx: Int,
    listState: LazyListState,
    listPadding: Int
) {

    item {
        Spacer(modifier = modifier.height(30.dp))
    }

    item {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(color = Color.Cyan),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(text = "TEST", fontStyle = FontStyle.Normal, fontSize = 40.sp)
        }
    }

    item {
        Box(
            modifier = modifier
                .fillParentMaxWidth()
                .height(500.dp)
                .background(color = Color.Magenta)
        )
    }

    item {
        Box(
            modifier = modifier
                .fillParentMaxWidth()
                .height(500.dp)
                .background(color = Color.Blue)
        )
    }

    item {
        Box(
            modifier = modifier
                .fillParentMaxWidth()
                .height(500.dp)
                .background(color = Color.Magenta)
        )
    }

    item {
        Box(
            modifier = modifier
                .fillParentMaxWidth()
                .height(500.dp)
                .background(color = Color.Blue)
        )
    }
}