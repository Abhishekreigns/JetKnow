package com.reignscanary.jetknow.composables


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIndicator(
    isLoading : Boolean
){

    if(isLoading){
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(20.dp)
                    .requiredSize(120.dp)
                    .clip(shape = MaterialTheme.shapes.large)
                    .background(MaterialTheme.colors.surface)
                    .padding(20.dp)

                    ,
            color = MaterialTheme.colors.secondary,
                strokeWidth = 6.dp
                )
        }




}