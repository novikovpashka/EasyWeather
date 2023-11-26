package com.example.easyweather.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

object MainDestinations {
    const val HOME_ROUTE = "home"
    const val DAY_FORECAST_DETAIL_ROUTE = "day"
    const val DAY_ID_KEY = "dayId"
}

@Composable
fun rememberEasyweatherNavController(
    navController: NavHostController = rememberNavController()
): EasyweatherNavController = remember(navController) {
    EasyweatherNavController(navController)
}

class EasyweatherNavController(
    val navController: NavHostController
) {

    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun upPress() {
        navController.navigateUp()
    }

    fun navigateToDayDetail(
        dayId: Long, from: NavBackStackEntry
    ) {
        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.DAY_FORECAST_DETAIL_ROUTE}/$dayId")
        }

    }

}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED