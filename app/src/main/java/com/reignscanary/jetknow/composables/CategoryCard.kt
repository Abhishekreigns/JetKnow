package com.reignscanary.jetknow.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme

import androidx.compose.material.Surface
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun CategoryCard(modifier: Modifier,
    category: String,
                 isSelected : Boolean = false,
                 onSelectedCategoryChanged : (String) -> Unit
)
{ val roundedBox = RoundedCornerShape(10.dp)
    Surface(
        modifier= modifier.padding(0.1.dp),
        color = if(isSelected) Color(0xfffea3aa) else  Color.White

    ) {
        Row(
            modifier = Modifier
                .clip(roundedBox)
                .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectedCategoryChanged(category) }
            )
        ) {
            Text(text = category, textAlign = TextAlign.Center,modifier = Modifier
                .fillMaxSize(1f)
                )

        }


    }



}