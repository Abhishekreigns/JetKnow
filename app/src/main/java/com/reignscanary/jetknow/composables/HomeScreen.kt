package com.reignscanary.jetknow.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.reignscanary.jetknow.ui.theme.Shapes

@Composable
fun HostOfComposables(mainScreenViewModel: MainScreenViewModel = viewModel())
{
    val searchText :String by mainScreenViewModel.searchText.observeAsState("" )
val roundedBox = RoundedCornerShape(10.dp)
Column(modifier = Modifier.padding(8.dp)
        ) {

    SearchText(searchText = searchText) { mainScreenViewModel.onSearchTextChange(it) }

    CustomMapView(DEFAULT_LOCATION = LatLng(35.7676325, 51.3192201),modifier = Modifier
        .clip(roundedBox)
        .background(Color.Gray)

        )

}

    
    
}


@Composable
fun CustomMapView(DEFAULT_LOCATION: LatLng,modifier: Modifier = Modifier)
{

 AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context: Context ->
            MapView(context).apply {

                this.getMapAsync{

                    it.addMarker(
                        MarkerOptions()
                        .position(DEFAULT_LOCATION)
                        .title("Marker")
                    )
                }

            }
        },


        )

}

@Composable
fun SearchText(modifier: Modifier = Modifier,searchText : String,onSearchTextChange: (String) -> Unit)
{
    OutlinedTextField(value =  searchText,
        onValueChange = onSearchTextChange,
        modifier
            .fillMaxHeight(0.08f)
            .fillMaxWidth()
            .padding(4.dp)


    )
}