package com.example.easyweather.ui.home

import android.Manifest
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.easyweather.R
import com.example.easyweather.data.model.CityExternalModel
import com.example.easyweather.data.model.WeatherWithForecast
import com.example.easyweather.ui.WeatherUiState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlin.math.roundToInt

const val DURATION = 400
val SEARCH_FIELD_HEIGHT = 48.dp
val SEARCH_FIELD_HORIZONTAL_PADDING = 16.dp
val SEARCH_FIELD_CORNER_RADIUS = SEARCH_FIELD_HEIGHT / 2
val DEFAULT_SPACER_HEIGHT = 8.dp


//THIS IS TEST

@OptIn(
    ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class
)
@Composable
fun Home(
    onDayClick: (Long) -> Unit,
    onSaveCityClick: () -> Unit,
    onSearchCityTextField: (query: String) -> Unit,
    onOpenSearchedCity: (id: String) -> Unit,
    weatherState: WeatherUiState,
    searchedCities: State<List<CityExternalModel>>,
    locationPermissionBeenRequestedOnce: Boolean,
    setLocationPermissionFirstTimeRequest: () -> Unit,
    refreshCurrentLocationCity: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val systemBarPadding = WindowInsets.systemBars.asPaddingValues().calculateTopPadding()
    val bottomBarPadding = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()
    val screenHeight =
        LocalConfiguration.current.screenHeightDp.dp + systemBarPadding + bottomBarPadding

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val locationPermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_COARSE_LOCATION,
    )
    val listState = rememberLazyListState()

    val mainWeatherMaxHeightDp = screenHeight * 2 / 3
    val mainWeatherMaxHeightPx = with(LocalDensity.current) { mainWeatherMaxHeightDp.roundToPx() }
    val mainWeatherMinHeightDp = 200.dp
    val mainWeatherMinHeightPx = with(LocalDensity.current) { mainWeatherMinHeightDp.roundToPx() }


    val connection = remember(mainWeatherMaxHeightPx) {
        MainWeatherNestedScrollConnection(
            mainWeatherMaxHeightPx = mainWeatherMaxHeightPx,
            mainWeatherMinHeightPx = mainWeatherMinHeightPx,
            listState = listState
        )
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .nestedScroll(connection)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            if (locationPermissionState.status.shouldShowRationale) {
                LocationPermissionAlertDialog(
                    openPermissionRequest = {
                        locationPermissionState.launchPermissionRequest()
                        setLocationPermissionFirstTimeRequest()
                    },
                    description = stringResource(id = R.string.location_permission_dialog_rationale_text)
                )
            } else if (!locationPermissionState.status.isGranted && !locationPermissionBeenRequestedOnce) {
                LocationPermissionAlertDialog(
                    openPermissionRequest = locationPermissionState::launchPermissionRequest,
                    description = stringResource(id = R.string.location_permission_dialog_text)
                )
            }

            LaunchedEffect(key1 = locationPermissionState.status.isGranted) {
                refreshCurrentLocationCity()
            }

            var weatherOffset = remember { mutableIntStateOf(connection.listOffset) }
            var dragOffset by remember { mutableIntStateOf(0) }

            LazyColumn(
                state = listState,
                userScrollEnabled = true,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
//                item {
//                    Spacer(modifier = Modifier.height(systemBarPadding + SEARCH_FIELD_HEIGHT + DEFAULT_SPACER_HEIGHT * 2))
//                }

//                item {
//                    if (!locationPermissionState.status.isGranted && !locationPermissionState.status.shouldShowRationale && !locationPermissionBeenRequestedOnce) {
//                        Text(text = "You cant use location services")
//                        FilledTonalButton(onClick = { }) {
//                            Text(text = "Grant permission")
//                        }
//                    }
//                }

                weatherForSelectedCity(
                    weatherState = weatherState,
                    connection = connection,
                    maxHeight = mainWeatherMaxHeightDp,
                    maxHeightPx = mainWeatherMaxHeightPx,
                    minHeight = mainWeatherMinHeightDp,
                    minHeightPx = mainWeatherMinHeightPx,
                    listState = listState,
                    modifier = Modifier.offset {
                        IntOffset(x = 0, y = connection.listOffset).apply {
                            if (listState.isScrollInProgress) {
                                connection.updateMainOffsetByMainDrag(-mainWeatherMaxHeightPx + connection.listOffset)
                                Log.v(
                                    "mytag",
                                    "SCROLLING LIST: listOffset is ${connection.listOffset}, mainOffset is ${connection.mainOffset}"
                                )
                            }

                        }
                    }
                )
            }

            MainWeatherForSelectedCity(
                weatherState = weatherState,
                maxHeight = mainWeatherMaxHeightDp,
                maxHeightPx = mainWeatherMaxHeightPx,
                minHeight = mainWeatherMinHeightDp,
                minHeightPx = mainWeatherMinHeightPx,
                connection = connection,
                listState = listState,
                onMainWeatherDrag = {
                    Log.v("mytag", "UPDATING BY DRAG for $it px")
                    connection.updateListOffsetByMainDrag(it)
                    connection.updateMainOffsetByMainDrag(it)
                    Log.v(
                        "mytag",
                        "listOffset is ${connection.listOffset}, mainOffset is ${connection.mainOffset}"
                    )

//                    connection.updateMainOffsetByMainDrag(it)
//                    connection.updateListOffsetByMainDrag(mainWeatherMinHeightPx - it)
//                                    dragOffset = it
//                    draggingOffset = it
//                    val newHeight = mainHeightResult + it.roundToInt()
//                    mainHeightResult =
//                        newHeight.coerceIn(mainWeatherMinHeightPx, mainWeatherMaxHeightPx)
                },
                coroutineScope = coroutineScope
            )
        }

//            SearchScreenMask(
//                screenHeight = screenHeight,
//                screenWidth = screenWidth,
//                expanded = searchExpanded,
//                onClick = {
//                    CoroutineScope(Dispatchers.Main).launch {
//                        listState.scrollToItem(0)
//                    }
//                    searchExpanded = !searchExpanded
//                },
//                state = listState,
//                systemBarPadding = systemBarPadding
//            )
//
//            SearchScreen(
//                searchedCities = searchedCities.value,
//                weatherUiState = weatherState,
//                expanded = searchExpanded,
//                onClick = {
//                    CoroutineScope(Dispatchers.Main).launch {
//                        listState.scrollToItem(0)
//                    }
//                    searchExpanded = !searchExpanded
//                },
//                onTextSearchValueChange = { query ->
//                    onSearchCityTextField(query)
//                },
//                onSearchedCityClick = {
//                    onOpenSearchedCity(it.id)
//                },
//                systemBarPadding = systemBarPadding
//            )

    }
}

@Composable
fun SearchScreenMask(
    systemBarPadding: Dp,
    screenHeight: Dp,
    screenWidth: Dp,
    expanded: Boolean,
    onClick: () -> Unit,
    state: LazyListState
) {

    val fistItemIsVisible = remember { derivedStateOf { state.firstVisibleItemIndex == 0 } }
    val offset = remember { derivedStateOf { state.firstVisibleItemScrollOffset } }

    val textPadding by animateDpAsState(
        targetValue = if (!expanded) 0.dp else 16.dp,
        label = "textPadding",
        animationSpec = tween(
            durationMillis = DURATION / 2,
            easing = if (expanded) LinearOutSlowInEasing else FastOutSlowInEasing
        )
    )

    val radius by animateDpAsState(
        targetValue = if (expanded) 0.dp else SEARCH_FIELD_CORNER_RADIUS,
        label = "radius",
        animationSpec = tween(
            durationMillis = if (expanded) DURATION * 3 / 2 else DURATION,
            easing = if (expanded) LinearOutSlowInEasing else LinearOutSlowInEasing
        )
    )

    val width by animateDpAsState(
        targetValue = if (expanded) screenWidth else screenWidth - SEARCH_FIELD_HORIZONTAL_PADDING * 2,
        label = "horizontalPadding",
        animationSpec = tween(
            durationMillis = if (expanded) DURATION * 4 / 5 else DURATION * 4 / 5,
            easing = if (expanded) LinearOutSlowInEasing else FastOutSlowInEasing
        )
    )

    val topPadding by animateDpAsState(
        targetValue = if (expanded) 0.dp else systemBarPadding + DEFAULT_SPACER_HEIGHT,
        label = "horizontalPadding",
        animationSpec = tween(
            durationMillis = if (expanded) DURATION * 3 / 5 else DURATION * 4 / 5,
            easing = if (expanded) LinearOutSlowInEasing else FastOutSlowInEasing
        )
    )

    val topPaddingInverted by animateDpAsState(
        targetValue = if (!expanded) 0.dp else systemBarPadding + DEFAULT_SPACER_HEIGHT,
        label = "horizontalPadding",
        animationSpec = tween(
            durationMillis = if (expanded) DURATION * 3 / 5 else DURATION * 4 / 5,
            easing = if (expanded) LinearOutSlowInEasing else FastOutSlowInEasing
        )
    )

    val height by animateDpAsState(
        targetValue = if (expanded) screenHeight else SEARCH_FIELD_HEIGHT,
        label = "horizontalPadding",
        animationSpec = tween(
            durationMillis = if (expanded) DURATION else DURATION * 4 / 5,
            easing = if (expanded) LinearOutSlowInEasing else FastOutSlowInEasing
        )
    )

    Box(
        modifier = Modifier
//            .offset(y = if (!fistItemIsVisible.value) (-200).dp else 0.dp)
//            .offset(y = -offset.value.pxToDp())
            .padding(top = topPadding)
            .width(width)
            .height(height)
            .clip(
                RoundedCornerShape(radius)
            )
            .background(color = Color.Blue)
            .clickable { onClick() }
    )

    {
        AnimatedVisibility(
            visible = !expanded,
            enter = fadeIn(animationSpec = tween(durationMillis = 0)),
            exit = fadeOut(animationSpec = tween(durationMillis = 0))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Gray)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = topPaddingInverted),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.background(color = Color.Green)
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(start = textPadding)
                            .background(color = Color.Green),
                        text = "Search city",
                        style = LocalTextStyle.current
                    )
                }
            }
        }
    }
}

@Composable
fun SearchScreen(
    weatherUiState: WeatherUiState,
    expanded: Boolean,
    onClick: () -> Unit,
    onSearchedCityClick: (CityExternalModel) -> Unit,
    onTextSearchValueChange: (String) -> Unit,
    searchedCities: List<CityExternalModel>,
    systemBarPadding: Dp
) {

    var text by remember { mutableStateOf("") }

    AnimatedVisibility(
        visible = expanded,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = DURATION * 3 / 2,
                easing = LinearEasing
            )
        ),
        exit = fadeOut(animationSpec = tween(durationMillis = 0))
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = systemBarPadding + DEFAULT_SPACER_HEIGHT / 2
                )
                .clickable { onClick() }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = {
                        Text(text = "Search city")
                    },
                    value = text,
                    onValueChange = {
                        text = it
                        onTextSearchValueChange(it)
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(bottom = DEFAULT_SPACER_HEIGHT)
            )


            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                userScrollEnabled = true,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(DEFAULT_SPACER_HEIGHT)
            ) {

                items(items = searchedCities, key = { item: CityExternalModel ->
                    item.id
                }) { city ->
                    Row(modifier = Modifier) {
                        SearchCityItem(city = city,
                            onClick = { onSearchedCityClick(city) })
                    }
                }


                when (weatherUiState) {
                    is WeatherUiState.Loading -> Unit
                    is WeatherUiState.Error -> Unit
                    is WeatherUiState.Success -> {
                        item {
                            if (weatherUiState.weather.isNotEmpty() && searchedCities.isNotEmpty()) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = DEFAULT_SPACER_HEIGHT / 2)
                                )
                            }
                        }
                        items(items = weatherUiState.weather, key = { item: WeatherWithForecast ->
                            item.id
                        }) { weatherWithForecast ->
                            Row(
                                modifier = Modifier
                            ) {
                                SavedCityWeatherItem(weather = weatherWithForecast,
                                    onClick = { })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchCityItem(city: CityExternalModel, onClick: () -> Unit) {
    Surface {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Gray)
                .padding(vertical = 8.dp)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "${city.city}, ${city.country} ")
        }
    }
}


@Composable
fun SavedCityWeatherItem(weather: WeatherWithForecast, onClick: () -> Unit) {
    Surface {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(color = Color.Magenta)
                .padding(horizontal = 16.dp)
                .clickable { onClick() },
            contentAlignment = Alignment.CenterStart
        ) {
            Text(text = "${weather.weather.city}, ${weather.weather.country}, ${weather.weather.tempC} celsius ")
        }
    }
}

@Composable
fun CurrentWeatherInfo() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

    }
}

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

class MainWeatherNestedScrollConnection(
    private val mainWeatherMaxHeightPx: Int,
    private val mainWeatherMinHeightPx: Int,
    private val listState: LazyListState
) : NestedScrollConnection {

    var mainOffset: Int by mutableIntStateOf(0)
        private set

    var listOffset: Int by mutableIntStateOf(mainWeatherMaxHeightPx)
        private set


    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {

        val delta = available.y.roundToInt()

        val newListOffset = if (listState.canScrollBackward) 0 else listOffset + delta
        val previousListOffset = listOffset

        val newMainOffset = if (listState.canScrollBackward) mainOffset else mainOffset + delta
        mainOffset = newMainOffset.coerceIn(-mainWeatherMaxHeightPx + mainWeatherMinHeightPx, 0)

        listOffset = newListOffset.coerceIn(mainWeatherMinHeightPx, mainWeatherMaxHeightPx)

        val consumed = listOffset - mainWeatherMaxHeightPx

        if (listOffset > mainWeatherMinHeightPx) {
            return Offset(0f, consumed.toFloat())
        }

        return Offset.Zero
    }

    fun updateListOffsetByMainDrag(offset: Int) {
        this.listOffset = mainWeatherMaxHeightPx + offset
    }

    fun updateMainOffsetByMainDrag(offset: Int) {
        this.mainOffset = offset
    }


}
