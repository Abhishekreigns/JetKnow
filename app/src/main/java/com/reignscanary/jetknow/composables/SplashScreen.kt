package com.reignscanary.jetknow.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.reignscanary.jetknow.R


@Composable
fun SplashScreen()
{


Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.Center) {

    Text(modifier = Modifier,text="JetKnow", style = TextStyle(fontSize =28.sp,fontFamily = FontFamily(Font(R.font.opnsasnsemibold))))

}


}
