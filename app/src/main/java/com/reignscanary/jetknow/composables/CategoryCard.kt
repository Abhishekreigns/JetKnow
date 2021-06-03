package com.reignscanary.jetknow.composables

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.database.FirebaseDatabase
import com.reignscanary.jetknow.R


@Composable
fun CategoryCard(modifier: Modifier,
    category: String,
                 isSelected : Boolean = false,
                 onSelectedCategoryChanged : (String) -> Unit
)
{ val roundedBox = RoundedCornerShape(10.dp)
    val context = LocalContext.current
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Categories")
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
                      Toast.makeText(context,"${myRef.child(category)}",Toast.LENGTH_SHORT).show()
                    onSelectedCategoryChanged(category) }
            )
        ) {
            Text(text = category, textAlign = TextAlign.Center,modifier = Modifier
                .fillMaxSize(1f)
                )

        }


    }



}