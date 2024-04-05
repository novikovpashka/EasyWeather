package com.example.easyweather.ui.home

import android.util.Log
import androidx.compose.animation.SplineBasedFloatDecayAnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.generateDecayAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.stopScroll
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.easyweather.ui.WeatherUiState
import kotlinx.coroutines.CoroutineScope
import kotlin.math.roundToInt

enum class DragValue { Start, End }

enum class DragState { COLLAPSED, EXPANDED, IN_PROGRESS }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainWeatherForSelectedCity(
    weatherState: WeatherUiState,
    maxHeight: Dp,
    modifier: Modifier = Modifier,
    connection: MainWeatherNestedScrollConnection,
    listState: LazyListState,
    onMainWeatherDrag: (delta: Int) -> Unit,
    minHeight: Dp,
    coroutineScope: CoroutineScope,
    maxHeightPx: Int,
    minHeightPx: Int,
    onDragStateChange: (DragState) -> Unit
) {

    val density = LocalDensity.current

    val anim =
        SplineBasedFloatDecayAnimationSpec(density = LocalDensity.current).generateDecayAnimationSpec<Float>()

    val end = -maxHeight + minHeight
    val endPx = with(density) {
        end.toPx()
    }

    var previousListOffset by remember { mutableIntStateOf(maxHeightPx) }

    var scrollingUp by remember {
        mutableStateOf(false)
    }

    val anchors = DraggableAnchors {
        DragValue.Start at 0f
        DragValue.End at endPx
    }

    val dragState = remember {
        AnchoredDraggableState(
            initialValue = DragValue.Start,
            positionalThreshold = { totalDistance: Float -> totalDistance * 0.2f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            snapAnimationSpec = tween(),
            decayAnimationSpec = anim
        )
    }.apply {
        updateAnchors(anchors)
    }

    val isDragged = listState.interactionSource.collectIsDraggedAsState()

//    LaunchedEffect(key1 = !listState.isScrollInProgress) {
//        if (dragState.requireOffset() != anchors.maxAnchor() || dragState.requireOffset() != anchors.minAnchor())
//            if (scrollingUp)
//                dragState.animateTo(DragValue.End)
//            else dragState.animateTo(DragValue.Start)
//    }

    LaunchedEffect(key1 = !isDragged.value) {
        Log.v("mytag", "isDragged is ${isDragged.value}")
        if (scrollingUp)
            dragState.animateTo(DragValue.End)
        else dragState.animateTo(DragValue.Start)
    }

    LaunchedEffect(key1 = dragState.requireOffset()) {
        if (dragState.requireOffset() == 0f) onDragStateChange(DragState.EXPANDED)
        else if (dragState.requireOffset() == endPx) onDragStateChange(DragState.COLLAPSED)
        else onDragStateChange(DragState.IN_PROGRESS)
        listState.stopScroll()
    }

    LaunchedEffect(key1 = connection.mainOffset) {
        scrollingUp = connection.mainOffset < previousListOffset
        previousListOffset = connection.mainOffset

        if (isDragged.value)
            dragState.anchoredDrag {
                this.dragTo(connection.mainOffset.toFloat(), 0f)
            }
    }


    Box(
        modifier = modifier
            .anchoredDraggable(
                state = dragState,
                orientation = Orientation.Vertical
            )
            .zIndex(1f)
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 24.dp,
                    bottomEnd = 24.dp
                )
            )
            .background(color = Color.Red.copy(alpha = 1f))
            .height(
                (maxHeightPx + dragState
                    .requireOffset()
                    .roundToInt())
                    .pxToDp()
            )
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "HELLO")

    }
}


