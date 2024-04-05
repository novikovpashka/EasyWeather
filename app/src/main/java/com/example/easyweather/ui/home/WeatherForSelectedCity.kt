package com.example.easyweather.ui.home

import androidx.compose.animation.core.FloatExponentialDecaySpec
import androidx.compose.animation.core.generateDecayAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyweather.ui.WeatherUiState
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.weatherForSelectedCity(
    weatherState: WeatherUiState,
    modifier: Modifier,
    connection: MainWeatherNestedScrollConnection,
    maxHeight: Dp,
    maxHeightPx: Int,
    minHeight: Dp,
    minHeightPx: Int,
    listState: LazyListState
) {

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