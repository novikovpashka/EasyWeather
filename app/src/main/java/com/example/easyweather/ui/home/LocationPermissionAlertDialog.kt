package com.example.easyweather.ui.home

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LocationPermissionAlertDialog(
    premissionDeclinedCount: Int,
    openPermissionRequest: () -> Unit,
    locationPermissionBeenGrantedOnce: Boolean
) {
    AlertDialog(
        onDismissRequest = openPermissionRequest,
        confirmButton = {
            Button(
                onClick = openPermissionRequest,
            ) {
                Text(text = "Grant permission")
            }
        },
        text = {
            if (premissionDeclinedCount == 0) {
                Text(text = "first attempt")
            }
            else if (premissionDeclinedCount == 1) {
                Text(text = "second attempt")
            }

        },
        title = { Text(text = "Location access required") },

        )
}

