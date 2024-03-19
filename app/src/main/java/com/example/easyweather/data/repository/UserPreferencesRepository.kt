package com.example.easyweather.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

    val userDataFlow: Flow<UserData> = dataStore.data.map { preferences ->
            val locationPermissionBeenRequestedOnce =
                preferences[PreferencesKeys.LOCATION_PERMISSION_BEEN_REQUESTED_ONCE] ?: false
            UserData(
                locationPermissionBeenRequestedOnce
            )
        }

    suspend fun setLocationPermissionBeenRequestedOnce() {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LOCATION_PERMISSION_BEEN_REQUESTED_ONCE] = true
        }
    }

    private object PreferencesKeys {
        val LOCATION_PERMISSION_BEEN_REQUESTED_ONCE =
            booleanPreferencesKey("location_permission_been_requested_once")
    }

    data class UserData(
        val locationPermissionBeenRequestedOnce: Boolean
    )

}