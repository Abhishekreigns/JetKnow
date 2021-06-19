package com.reignscanary.jetknow.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reignscanary.jetknow.R
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reignscanary.jetknow.MainScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun CategoryCard(modifier: Modifier,
    category: String,
                 isSelected : Boolean = false,
                 onSelectedCategoryChanged : (String) -> Unit
)
{
val mainScreenViewModel : MainScreenViewModel = viewModel()
 val context : Context= LocalContext.current
val coroutineScope = rememberCoroutineScope()
    Surface(
        modifier= modifier
            .padding(top = 2.dp, bottom = 2.dp, end = 2.dp)
            .clip(MaterialTheme.shapes.medium),
        color = if(isSelected)
            MaterialTheme.colors.secondary
        else
            MaterialTheme.colors.surface

    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium)

                .toggleable(
                    value = isSelected,
                    onValueChange = {

                        if (it) {
                            coroutineScope.launch {
                                mainScreenViewModel.Search(category, context)
                                onSelectedCategoryChanged(category)
                                mainScreenViewModel.onListOfLatLngChangedStatus(true)
                            }
                        }
                        //To switch off the chip when user clicks the chip again
                        else {
                            onSelectedCategoryChanged("")
                            mainScreenViewModel.onListOfLatLngChanged(HashMap())
                            mainScreenViewModel.onListOfLatLngChangedStatus(false)
                        }
                    }

                )

        ) {
            Text(text = category, textAlign = TextAlign.Center,modifier = Modifier
                .weight(3f),
                style = TextStyle(color = MaterialTheme.colors.onSecondary,fontSize = 14.sp,fontFamily = FontFamily(
                    Font(R.font.opnsansregular)))
                )

        }


    }



}
