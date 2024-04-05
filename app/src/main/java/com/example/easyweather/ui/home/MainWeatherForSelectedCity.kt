package com.example.easyweather.ui.home

import android.util.Log
import androidx.annotation.FloatRange
import androidx.compose.animation.SplineBasedFloatDecayAnimationSpec
import androidx.compose.animation.core.DecayAnimation
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FloatDecayAnimationSpec
import androidx.compose.animation.core.FloatExponentialDecaySpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.generateDecayAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDragScope
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.snapTo
import androidx.compose.foundation.gestures.stopScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.systemGestureExclusion
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.easyweather.ui.WeatherUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

enum class DragValue { Start, End }

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
    minHeightPx: Int
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
            snapAnimationSpec = tween(easing = FastOutSlowInEasing),
            decayAnimationSpec = anim
        )
    }.apply {
        updateAnchors(anchors)
    }

    LaunchedEffect(key1 = connection.mainOffset) {
        if (listState.isScrollInProgress) {
            Log.v("mytag", "===DRAG INSIDE=== for ${connection.mainOffset}")
            scrollingUp = connection.listOffset < previousListOffset
            previousListOffset = connection.listOffset
            dragState.anchoredDrag {
                this.dragTo(connection.mainOffset.toFloat(), 100f)
            }
        }
    }
//
    LaunchedEffect(key1 = !listState.isScrollInProgress && !listState.canScrollBackward) {
        Log.v("mytag", "ANIMATING")
        if (scrollingUp)
            dragState.animateTo(DragValue.End)
        else dragState.animateTo(DragValue.Start)

//            dragState.animateTo(anchors.closestAnchor(dragState.offset) as DragValue)
    }

    Box(
        modifier = modifier
            .offset {
                IntOffset(
                    0,
                    if (listState.isScrollInProgress)
                        connection.mainOffset
//                    else if (listState.canScrollBackward && !listState.isScrollInProgress) connection.mainOffset
                        else dragState
                        .requireOffset()
                        .roundToInt()
                        .apply {
                            onMainWeatherDrag(this)
                        }
                )
            }
            .anchoredDraggable(
                state = dragState,
                orientation = Orientation.Vertical
            )
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
                maxHeight
            )
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "HELLO")

    }
}


