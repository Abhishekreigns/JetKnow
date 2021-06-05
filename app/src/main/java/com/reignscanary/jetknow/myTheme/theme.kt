package com.reignscanary.jetknow.myTheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColors = lightColors(
    primary = greenLightThemePrimary,
    onPrimary = Color.Black,
    secondary = redLightThemeSecondary,
    onSecondary = Color.White,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black
)



private val DarkColors = darkColors(

    primary = greenDarkThemePrimary,
    onPrimary = Color.Black,
    secondary = redDarkThemeSecondary,
    onSecondary = Color.White,
    background = Color.Black,
    onBackground = Color.White,
    surface = Color.DarkGray,
    onSurface = Color.White


)



@Composable
fun JetKnowTheme(content : @Composable () -> Unit){

MaterialTheme(content = content,
    shapes = roundedShapes
    ,colors = if(isSystemInDarkTheme())  DarkColors else LightColors)



}