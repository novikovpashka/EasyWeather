package com.example.easyweather

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.easyweather.ui.HomeViewModel
import com.example.easyweather.ui.day.Day
import com.example.easyweather.ui.home.Home
import com.example.easyweather.ui.navigation.MainDestinations
import com.example.easyweather.ui.navigation.rememberEasyWeatherNavController
import com.example.easyweather.ui.theme.EasyweatherTheme


@Composable
fun EasyWeatherApp() {
    EasyweatherTheme {
        val ewNavController = rememberEasyWeatherNavController()
        NavHost(
            navController = ewNavController.navController,
            startDestination = MainDestinations.HOME_ROUTE
        ) {
            ewNavGraph(
                onDaySelect = ewNavController::navigateToDayDetail
            )
        }
    }
}

private fun NavGraphBuilder.ewNavGraph(
    onDaySelect: (Long, NavBackStackEntry) -> Unit
) {
    composable(route = MainDestinations.HOME_ROUTE) { from ->
        val viewModel: HomeViewModel = hiltViewModel()
        val weather by viewModel.weatherUiState.collectAsState()
        val searchedCities = viewModel.searchedCities.collectAsState()
        val locationPermissionFirstTimeRequested by viewModel.locationPermissionFirstTimeRequested.collectAsState()

        Home(
            onDayClick = { day ->
                onDaySelect(day, from)
            },
            onSaveCityClick = { },
            onSearchCityTextField = { query ->
                viewModel.searchCity(query)
            },
            onOpenSearchedCity = {},
            weatherState = weather,
            searchedCities = searchedCities,
            locationPermissionBeenRequestedOnce = locationPermissionFirstTimeRequested,
            setLocationPermissionFirstTimeRequest = {
                viewModel.setLocationPermissionBeenRequestedOnce()
            },
            refreshCurrentLocationCity = viewModel::refreshCurrentLocationCity
        )
    }

    composable(
        "${MainDestinations.DAY_FORECAST_DETAIL_ROUTE}/{${MainDestinations.DAY_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.DAY_ID_KEY) {
            type = NavType.LongType
        })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val dayId = arguments.getLong(MainDestinations.DAY_ID_KEY)
        Day()
    }

}