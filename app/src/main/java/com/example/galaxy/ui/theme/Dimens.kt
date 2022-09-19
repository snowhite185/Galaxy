package com.example.galaxy.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Dimensions(
    val screenPaddingStart: Dp,
    val screenPaddingEnd: Dp,
    val screenPaddingTop: Dp,
    val screenPaddingBottom: Dp,
)

val portraitDimensions = Dimensions(
    screenPaddingStart = 20.dp,
    screenPaddingEnd = 20.dp,
    screenPaddingTop = 60.dp,
    screenPaddingBottom = 20.dp
)

val landScapeDimensions = Dimensions(
    screenPaddingStart = 50.dp,
    screenPaddingEnd = 20.dp,
    screenPaddingTop = 20.dp,
    screenPaddingBottom = 20.dp
)