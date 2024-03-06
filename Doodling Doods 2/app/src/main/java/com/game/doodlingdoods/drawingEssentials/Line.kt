package com.game.doodlingdoods.drawingEssentials

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Line(
    val start: Offset,
    val end: Offset,
    val color: Color = Color.Black,
    val strokeWidth: Dp = 8.dp,
    )

data class LinesStorage(
    val lines: MutableList<Line>
)

