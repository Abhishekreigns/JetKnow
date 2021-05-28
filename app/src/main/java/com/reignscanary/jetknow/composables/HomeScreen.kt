package com.reignscanary.jetknow.composables


import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.reignscanary.jetknow.MainActivity


@Composable
fun HostOfComposables(mainScreenViewModel: MainScreenViewModel = viewModel(),locationManager: LocationManager,savedInstanceState : Bundle?)
{  var context = LocalContext.current
    var latLng = remember {
        mutableStateOf(LatLng(12.834174,79.703644))
    }
    val searchText :String by mainScreenViewModel.searchText.observeAsState("" )
val roundedBox = RoundedCornerShape(16.dp)
Column(modifier = Modifier
    .padding(8.dp)
    .fillMaxSize(1f)
        ) {

    SearchText(searchText = searchText,modifier = Modifier.fillMaxWidth(0.9f)) { mainScreenViewModel.onSearchTextChange(it) }
 Spacer(modifier = Modifier.padding(10.dp))
    CustomMapView(
        DEFAULT_LOCATION = latLng.value,
        savedInstanceState = savedInstanceState, modifier = Modifier
            .padding(2.dp)
            .fillMaxHeight(0.9f)
            .clip(roundedBox)
    ){mainScreenViewModel.onLatLngUpdate(it)}
    Spacer(modifier = Modifier.padding(2.dp))
    FloatingActionButton(onClick = {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return@FloatingActionButton

        }
        else{

            var location : Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location != null) {
                Toast.makeText(
                    context,
                    "${location.latitude} ${location.longitude}",
                    Toast.LENGTH_SHORT
                ).show()
              //  latLng.value = LatLng(location.latitude, location.longitude)



            }
            latLng.value = LatLng(12.834174,79.703564)
        }





    }) {

    }

}
    
    
}


@Composable
fun CustomMapView(
    DEFAULT_LOCATION: LatLng,
    savedInstanceState: Bundle?,
    modifier: Modifier = Modifier,
    onLatLngUpdate: (LatLng) -> Unit
)
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