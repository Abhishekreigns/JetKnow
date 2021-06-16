package com.reignscanary.jetknow.composables


import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIndicator(
    isLoading : Boolean
){

    if(isLoading){
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(20.dp),
            color = MaterialTheme.colors.secondary
                )
        }




}