package com.reignscanary.jetknow.composables


import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
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
import com.reignscanary.jetknow.lastLocation


@Composable
fun HostOfComposables(mainScreenViewModel: MainScreenViewModel = viewModel(), locationManager: LocationManager, savedInstanceState : Bundle?)
{
var context = LocalContext.current
    SideEffect {

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            Toast.makeText(
                context,
                "${locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.longitude}",
                Toast.LENGTH_SHORT
            ).show()
            lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!
            mainScreenViewModel.onLatLngUpdate(LatLng(lastLocation.latitude, lastLocation.longitude))
            return@SideEffect
        }
    }





    var latLng = remember {
        mutableStateOf(LatLng(0.0,0.0))
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

       latLng.value = mainScreenViewModel.latLng.value?.let { LatLng(it.latitude,
           mainScreenViewModel.latLng.value!!.longitude) }!!

    }) {

    }

}
SideEffect {

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