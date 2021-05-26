package com.reignscanary.jetknow.composables


import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions


@Composable
fun HostOfComposables(mainScreenViewModel: MainScreenViewModel = viewModel(),savedInstanceState : Bundle?)
{ val scrollState = rememberScrollState()

    val searchText :String by mainScreenViewModel.searchText.observeAsState("" )
val roundedBox = RoundedCornerShape(16.dp)
Column(modifier = Modifier
    .padding(8.dp)
    .fillMaxSize(1f)
        ) {

    SearchText(searchText = searchText,modifier = Modifier.fillMaxWidth(0.9f)) { mainScreenViewModel.onSearchTextChange(it) }
 Spacer(modifier = Modifier.padding(10.dp))
    CustomMapView(DEFAULT_LOCATION = LatLng(35.7676325, 51.3192201),
        savedInstanceState = savedInstanceState,modifier = Modifier
            .padding(2.dp)
            .fillMaxHeight(0.9f)
            .clip(roundedBox))
    Spacer(modifier = Modifier.padding(2.dp))
    FloatingActionButton(onClick = { /*TODO*/ }) {
        

    }

}

    
    
}


@Composable
fun CustomMapView(DEFAULT_LOCATION: LatLng, savedInstanceState : Bundle?, modifier: Modifier = Modifier)
{
val mapView = MapView(LocalContext.current)
 AndroidView(
     modifier = modifier.fillMaxSize(),
     factory = { mapView.apply {

         this.onCreate(savedInstanceState)
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
            .fillMaxHeight(0.1f)
            .fillMaxWidth()
            .padding(4.dp),



    )
}