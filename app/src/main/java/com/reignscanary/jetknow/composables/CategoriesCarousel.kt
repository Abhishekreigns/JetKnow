package com.reignscanary.jetknow.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun CategoriesCarousel(modifier: Modifier)
{ // val scrollState = rememberScrollState()
    LazyRow(modifier = Modifier
        .background(Color.White)

        .padding(top= 10.dp)
        .clip(RoundedCornerShape(10.dp))


    ) {
        item {


            for(i in 1..50 ){

                Card(
                    modifier = modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(enabled = true,onClickLabel = "HI",onClick = {/*TODO*/})) {
                    Text(text = "Hi There",modifier= modifier.background(Color.White),textAlign = TextAlign.Center)

                }
            }


        }

    }


}