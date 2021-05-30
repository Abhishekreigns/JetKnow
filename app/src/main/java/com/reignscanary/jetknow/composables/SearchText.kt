package com.reignscanary.jetknow.composables

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchText(modifier: Modifier = Modifier, searchText : String, onSearchTextChange: (String) -> Unit)
{
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        modifier
            .fillMaxHeight(0.1f)
            .fillMaxWidth()
            .padding(4.dp))
}