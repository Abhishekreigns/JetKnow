package com.reignscanary.jetknow.myTheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColors = lightColors(
    primary = red,
    primaryVariant = green,
    onPrimary = Color.White,
    secondary = green ,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black
)



private val DarkColors = darkColors(

    primary = lightRed,
    primaryVariant = darkGreen,
    onPrimary = Color.Black,
    secondary = darkGreen,
    background = darkGray,
    onBackground = Color.White,
    surface = Color.DarkGray,
    onSurface = Color.White


)





@Composable
fun JetKnowTheme(content : @Composable () -> Unit){

MaterialTheme(content = content,colors = if(isSystemInDarkTheme())  DarkColors else LightColors)


}