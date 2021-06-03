package com.reignscanary.jetknow.composables

import android.content.Context
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun LoadingScreen()
{

 CircularProgressIndicator(progress = 0.5f)

}