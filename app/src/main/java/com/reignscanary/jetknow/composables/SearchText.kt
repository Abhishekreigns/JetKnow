package com.reignscanary.jetknow.composables


import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.google.firebase.database.*
import com.reignscanary.jetknow.R
import com.google.firebase.database.DataSnapshot





@Composable
fun SearchText(modifier: Modifier = Modifier, searchText : String, onSearchTextChange: (String) -> Unit)
{


    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        singleLine = true
        ,modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
        textStyle = TextStyle(
            color = MaterialTheme.colors.onSurface,
            fontFamily = FontFamily(Font(R.font.opnsasnsemibold))
        )

    )

}