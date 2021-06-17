package com.reignscanary.jetknow.composables


import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reignscanary.jetknow.R
import com.reignscanary.jetknow.MainScreenViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

@Composable
fun SearchText(modifier: Modifier = Modifier, searchText : String, onSearchTextChange: (String) -> Unit)
{
    val focusManager = LocalFocusManager.current
  val mainScreenViewModel : MainScreenViewModel = viewModel()
    val context  = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        singleLine = true
        ,modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
        ,
        textStyle = TextStyle(
            color = MaterialTheme.colors.onSurface,
            fontFamily = FontFamily(Font(R.font.opnsasnsemibold))
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions (

            onSearch = {

                focusManager.clearFocus()
                coroutineScope.launch {
                mainScreenViewModel.Search(searchText, context)
                mainScreenViewModel.onListOfLatLngChangedStatus(true)
            }
            }
                ),
        placeholder = {
            Text(text = "Search for a service")
        }

    )






}