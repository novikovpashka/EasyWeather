package com.example.easyweather.ui.home

import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.easyweather.R
import com.example.easyweather.data.model.CityExternalModel
import com.example.easyweather.data.model.WeatherWithForecast
import com.example.easyweather.data.model.getCityAndCountry
import com.example.easyweather.ui.HomeViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.flow.MutableStateFlow


@OptIn(
    ExperimentalPermissionsApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun Home(
    onDayClick: (Long) -> Unit, viewModel: HomeViewModel = hiltViewModel()
) {
    val savedCitiesWeather by viewModel.savedCitiesWeather.collectAsState()
    val currentWeather by viewModel.weather.collectAsState()
    val currentState by viewModel.state.collectAsState()
    val locationPermissionFirstTimeRequested by viewModel.locationPermissionFirstTimeRequested.collectAsState()
    val locationPermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_COARSE_LOCATION,
    )
    val cities = viewModel.searchedCities.collectAsState()
    var searchText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    var searchExpanded by remember { mutableStateOf(false) }
    var searchVisible by remember { mutableStateOf(false) }


    Surface {
        Box(
            modifier = Modifier.fillMaxSize()
//                .safeDrawingPadding()
            , contentAlignment = Alignment.TopCenter
        ) {
            //Logic to check location permission including first time app opened or
            // after user turned off location permission manually
            if (locationPermissionState.status.shouldShowRationale) {
                LocationPermissionAlertDialogTest(
                    openPermissionRequest = {
                        locationPermissionState.launchPermissionRequest()
                        viewModel.setLocationPermissionFirstTimeRequest(false)
                    },
                    description = stringResource(id = R.string.location_permission_dialog_rationale_text)
                )
            } else if (!locationPermissionState.status.isGranted && locationPermissionFirstTimeRequested) {
                LocationPermissionAlertDialogTest(
                    openPermissionRequest = locationPermissionState::launchPermissionRequest,
                    description = stringResource(id = R.string.location_permission_dialog_text)
                )
            }



            LazyColumn(
                state = listState,
                userScrollEnabled = true,
                flingBehavior = ScrollableDefaults.flingBehavior()
            ) {

                item {
                    SearchFieldMask(onClick = {
                        searchExpanded = !searchExpanded
                        searchVisible = true
                    })
                }

                item {
                    Spacer(modifier = Modifier.height(72.dp))
                }

                item {

                    Text(text = "Permission granted is ${locationPermissionState.status}")
                    Text(text = currentWeather)
                    Text(text = currentState)

                    FilledTonalButton(onClick = {
                        viewModel.refreshCurrentCity()
                    }) {

                    }

                    if (!locationPermissionState.status.isGranted && !locationPermissionState.status.shouldShowRationale && !locationPermissionFirstTimeRequested) {
                        Text(text = "You cant use location services")
                        FilledTonalButton(onClick = { }) {
                            Text(text = "Grant permission")
                        }
                    }

                    TextField(value = searchText, onValueChange = {
                        searchText = it
                        viewModel.searchCity(it)
                    }, label = { Text("Label") })
                }

                items(items = cities.value, key = { item: CityExternalModel ->
                    item.id
                }) { city ->
                    Row(modifier = Modifier.animateItemPlacement()) {
                        SearchCityItem(city = city,
                            onClick = { viewModel.saveCity(city.getCityAndCountry()) })
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                items(items = savedCitiesWeather, key = { item: WeatherWithForecast ->
                    item.id
                }) { weatherWithForecast ->
                    Row(modifier = Modifier.animateItemPlacement()) {
                        SavedCityWeatherItem(weather = weatherWithForecast,
                            onClick = { viewModel.deleteSavedCity(weatherWithForecast.id) })
                    }
                }
            }

            SearchField(
                cities = savedCitiesWeather,
                searchExpanded,
                searchVisible,
                onClick = { searchExpanded = !searchExpanded },
                onCloseAnimationEnd = { searchVisible = false }
            )


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
                .background(color = Color.Gray)
                .padding(horizontal = 8.dp)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "${weather.weather.city}, ${weather.weather.country}, ${weather.weather.tempC} celsius ")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchField(
    cities: List<WeatherWithForecast>,
    expanded: Boolean,
    visible: Boolean,
    onClick: () -> Unit,
    onCloseAnimationEnd: () -> Unit
) {
    val offset by animateIntOffsetAsState(
        targetValue = if (!expanded) {
            IntOffset(0, 150)
        } else {
            IntOffset.Zero
        },
        label = "offset",
    )
    val horizontalPadding: Dp by animateDpAsState(
        if (expanded) {
            0.dp
        } else {
            8.dp
        }, label = "horizontalPadding"
    )

    val alpha by animateFloatAsState(
        targetValue = if (expanded) {
            1f
        } else {
            0f
        },
        animationSpec = tween(),
        label = "alpha"
    )

    Box(modifier = Modifier
        .alpha(if (expanded) 1f else alpha)
        .fillMaxWidth()
        .offset { offset }
        .padding(horizontal = horizontalPadding)
        .clip(shape = RoundedCornerShape(14.dp))
        .background(color = Color.Cyan)
        .clickable { onClick() }
        .animateContentSize(finishedListener = { initial, target ->
            if (target.height < initial.height) {
                onCloseAnimationEnd()
            }
        })
        .let {
            if (expanded) it.fillMaxSize() else it.height(48.dp)
        }, contentAlignment = Alignment.TopCenter
    ) {



        LazyColumn(
            userScrollEnabled = true,
            flingBehavior = ScrollableDefaults.flingBehavior()
        ) {
            item {
                Spacer(modifier = Modifier.height(54.dp))
            }

            items(items = cities, key = { item: WeatherWithForecast ->
                item.id
            }) { weatherWithForecast ->
                Row(modifier = Modifier.animateItemPlacement()) {
                    SavedCityWeatherItem(weather = weatherWithForecast,
                        onClick = {  })
                }
            }
        }



    }
}

@Composable
fun SearchFieldMask(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .offset(y = 55.dp)
            .padding(horizontal = 8.dp)
            .clip(shape = RoundedCornerShape(14.dp))
            .background(color = Color.Blue)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {



    }

}


