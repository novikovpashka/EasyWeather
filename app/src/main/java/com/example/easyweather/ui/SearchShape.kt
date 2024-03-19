package com.example.easyweather.ui

import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class SearchShape(
    private val cornerRadius: Dp = 24.dp,
    private val paddingTop: Dp,
    private val paddingBottom: Dp,
    private val paddingHorizontal: Dp
): Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val paddingTop = with(density) { paddingTop.toPx() }
        val paddingBottom = with(density) { paddingBottom.toPx() }
        val paddingHorizontal = with(density) { paddingHorizontal.toPx() }
        val cornerRadius = with(density) { cornerRadius.toPx() }
        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    left = 0f + paddingHorizontal/2,
                    top = paddingTop,
                    right = size.width - paddingHorizontal/2,
                    bottom = size.height - paddingBottom,
                    radiusX = cornerRadius,
                    radiusY = cornerRadius
                )
            )
            close()
        }
        return Outline.Generic(path)
    }
}