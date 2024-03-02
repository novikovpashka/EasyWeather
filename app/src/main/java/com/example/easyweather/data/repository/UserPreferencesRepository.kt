package com.example.easyweather.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data.map { preferences ->
            val locationPermissionFirstTimeRequest =
                preferences[PreferencesKeys.LOCATION_PERMISSION_FIRST_TIME_REQUEST] ?: true
            UserPreferences(
                locationPermissionFirstTimeRequest
            )
        }

    suspend fun setLocationPermissionPermanentlyDeclined(declined: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LOCATION_PERMISSION_FIRST_TIME_REQUEST] = declined
        }
    }

    private object PreferencesKeys {
        val LOCATION_PERMISSION_FIRST_TIME_REQUEST =
            booleanPreferencesKey("location_permission_permanently_declined")
    }

    data class UserPreferences(
        val locationPermissionFirstTimeRequest: Boolean
    )

}