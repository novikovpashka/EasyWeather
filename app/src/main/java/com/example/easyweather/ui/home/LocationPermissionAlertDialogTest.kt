package com.example.easyweather.ui.home

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.easyweather.R

@Composable
fun LocationPermissionAlertDialogTest(
    openPermissionRequest: () -> Unit,
    description: String
) {
    AlertDialog(
        onDismissRequest = openPermissionRequest,
        confirmButton = {
            Button(
                onClick = openPermissionRequest,
            ) {
                Text(text = stringResource(id = R.string.location_permission_dialog_ok_button_text))
            }
        },
        text = { Text(text = description) },
        title = { Text(text = stringResource(id = R.string.location_permission_dialog_title)) },
    )
}

