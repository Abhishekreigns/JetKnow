package com.reignscanary.jetknow.composables

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.FirebaseDatabase
import com.reignscanary.jetknow.R


@Composable
fun CategoryCard(modifier: Modifier,
    category: String,
                 isSelected : Boolean = false,
                 onSelectedCategoryChanged : (String) -> Unit
)
{
    val alpha: Float by animateFloatAsState(if (isSelected) 1f else 0.5f)
    val context = LocalContext.current
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Categories")
    Surface(
        modifier= modifier
            .padding(top = 8.dp, bottom = 8.dp,end = 1.dp)
            .clip(MaterialTheme.shapes.large),
        color = if(isSelected) MaterialTheme.colors.primaryVariant else  MaterialTheme.colors.primary

    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium)

                .toggleable(
                value = isSelected,
                onValueChange = {
                      Toast.makeText(context,"${myRef.child(category)}",Toast.LENGTH_SHORT).show()
                    onSelectedCategoryChanged(category) }
            )

        ) {
            Text(text = category, textAlign = TextAlign.Center,modifier = Modifier
                .fillMaxSize(1f),
                style = TextStyle(color = MaterialTheme.colors.onSecondary,fontSize = 14.sp)
                )

        }


    }



}