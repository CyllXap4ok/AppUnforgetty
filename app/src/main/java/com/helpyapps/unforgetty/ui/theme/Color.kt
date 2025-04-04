package com.helpyapps.unforgetty.ui.theme

import androidx.compose.ui.graphics.Color

val Color.Companion.GreenBlue: Color
    get() = Color(0, 100, 88, 255)
val Color.Companion.DeepDark: Color
    get() = Color(17, 17, 17, 255)
val Color.Companion.Skin: Color
    get() = Color(170, 170, 170, 255)

object LightColorPalette {
    val contentColor: Color = Color.GreenBlue
    val containerColor: Color = Color.White
    val hintColor: Color = Color.Gray
}

object DarkColorPalette {
    val contentColor: Color = Color.Skin
    val containerColor: Color = Color.DeepDark
    val hintColor: Color = Color.DarkGray
}