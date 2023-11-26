package com.example.easyweather.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FilledTonalButton

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun Home(onDayClick: (Long) -> Unit) {
    Surface {
        Box(
            modifier = Modifier
                .background(color = Color.Cyan)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            FilledTonalButton(onClick = {
                onDayClick (1)
            }) {

            }
        }
    }
}