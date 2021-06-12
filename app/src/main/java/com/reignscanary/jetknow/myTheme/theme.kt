package com.reignscanary.jetknow.myTheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


private val LightColors = lightColors(
    primary = lightThemePrimary,
    onPrimary = lightThemeOnPrimary,
    secondary = lightThemeSecondary,
    onSecondary = lightThemeOnSecondary,
    background = lightThemeBackGround,
    onBackground = lightThemeOnBackGround,
    surface = lightThemeSurface,
    onSurface = lightThemeOnSurface
)



private val DarkColors = darkColors(

    primary = darkThemePrimary,
    onPrimary = darkThemeOnPrimary,
    secondary = darkThemeSecondary,
    onSecondary = darkThemeOnSecondary,
    background = darkThemeBackGround,
    onBackground = darkThemeOnBackGround,
    surface = darkThemeSurface,
    onSurface = darkThemeOnSurface


)



@Composable
fun JetKnowTheme(content : @Composable () -> Unit){

MaterialTheme(content = content,
    shapes = roundedShapes
    ,colors = if(isSystemInDarkTheme())  DarkColors else LightColors)



}