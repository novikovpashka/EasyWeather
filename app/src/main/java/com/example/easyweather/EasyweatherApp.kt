package com.example.easyweather

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.easyweather.ui.day.Day
import com.example.easyweather.ui.home.Home
import com.example.easyweather.ui.navigation.MainDestinations
import com.example.easyweather.ui.navigation.rememberEasyweatherNavController
import com.example.easyweather.ui.theme.EasyweatherTheme


@Composable
fun EasyweatherApp() {
    EasyweatherTheme {
        val easyweatherNavController = rememberEasyweatherNavController()
        NavHost(
            navController = easyweatherNavController.navController,
            startDestination = MainDestinations.HOME_ROUTE
        ) {
            easyweatherNavGraph(
                onDaySelect = easyweatherNavController::navigateToDayDetail
            )
        }
    }
}

private fun NavGraphBuilder.easyweatherNavGraph(
    onDaySelect: (Long, NavBackStackEntry) -> Unit
) {
    composable(route = MainDestinations.HOME_ROUTE) { from ->
        Home(onDayClick = { day ->
            onDaySelect(day, from)
        })
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