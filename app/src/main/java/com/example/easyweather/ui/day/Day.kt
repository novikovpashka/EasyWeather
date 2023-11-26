package com.example.easyweather.ui.day

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Day() {
    Surface {
        Box(modifier = Modifier
            .background(color = Color.Green)
            .fillMaxSize())
    }
}