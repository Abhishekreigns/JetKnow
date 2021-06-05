package com.reignscanary.jetknow.composables


import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.google.firebase.database.FirebaseDatabase


@Composable
fun SearchText(modifier: Modifier = Modifier, searchText : String, onSearchTextChange: (String) -> Unit)
{
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Categories")
    val context = LocalContext.current
    Toast.makeText(context,"${myRef.child(searchText)}", Toast.LENGTH_SHORT).show()

    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        singleLine = true
        ,modifier = modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
            .padding(start = 20.dp,end = 10.dp,top = 10.dp,bottom = 10.dp),
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface)

    )

}