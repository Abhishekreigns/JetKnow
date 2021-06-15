package com.reignscanary.jetknow.composables

import android.R.attr
import android.widget.Toast
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
import com.google.firebase.database.*
import com.reignscanary.jetknow.Contributions
import com.reignscanary.jetknow.R
import android.R.attr.data
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.LatLng

import com.google.firebase.database.DataSnapshot
import com.reignscanary.jetknow.MainScreenViewModel
import com.reignscanary.jetknow.search


@Composable
fun CategoryCard(modifier: Modifier,
    category: String,
                 isSelected : Boolean = false,
                 onSelectedCategoryChanged : (String) -> Unit,
                 onListOfLatLngChanged :(HashMap<LatLng,String>) -> Unit
)
{
val mainScreenViewModel : MainScreenViewModel = viewModel()
 val context : Context= LocalContext.current

    Surface(
        modifier= modifier
            .padding(top = 2.dp, bottom = 2.dp, end = 2.dp)
            .clip(MaterialTheme.shapes.medium),
        color = if(isSelected) MaterialTheme.colors.secondary else  MaterialTheme.colors.surface

    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium)

                .toggleable(
                    value = isSelected,
                    onValueChange = {

                        onSelectedCategoryChanged(category)
                        onListOfLatLngChanged(search(category, context))
                        mainScreenViewModel.onSelectedChange(true)


                    }
                )

        ) {
            Text(text = category, textAlign = TextAlign.Center,modifier = Modifier
                .fillMaxSize(1f),
                style = TextStyle(color = MaterialTheme.colors.onSecondary,fontSize = 14.sp,fontFamily = FontFamily(
                    Font(R.font.opensansbold)))
                )

        }


    }



}
