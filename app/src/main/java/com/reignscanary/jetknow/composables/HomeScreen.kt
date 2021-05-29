package com.reignscanary.jetknow.composables



import android.content.Context
import android.location.Location

import android.os.Bundle
import android.widget.Toast

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlin.math.ln


@Composable
fun HostOfComposables(mainScreenViewModel: MainScreenViewModel = viewModel(),location: Location,savedInstanceState : Bundle?)
{
    val latLng  : LatLng by mainScreenViewModel.latLng.observeAsState(LatLng(-33.88,151.21))
    val searchText :String by mainScreenViewModel.searchText.observeAsState("" )
    val roundedBox = RoundedCornerShape(16.dp)


    //Align all the composables in a column
    Column(
        modifier = Modifier
    .padding(8.dp)
    .fillMaxSize(1f)
        )

    {
        SearchText(searchText = searchText,
            modifier = Modifier
                .fillMaxWidth(0.9f)
        ) {
            mainScreenViewModel.onSearchTextChange(it)
        }


        Spacer(modifier = Modifier.padding(10.dp))

        CustomMapView(
        DEFAULT_LOCATION = latLng,
        savedInstanceState = savedInstanceState,
        modifier = Modifier
            .padding(2.dp)
            .fillMaxHeight(0.9f)
            .clip(roundedBox)
    ){
        mainScreenViewModel.onLatLngUpdate(it)
    }
    Spacer(modifier = Modifier.padding(2.dp))

    FloatingActionButton(onClick = {
          //on Fab Click update the position value to the current location of the user
        mainScreenViewModel.onLatLngUpdate(LatLng(location.latitude,location.longitude))
    }
    ) {}

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


    val cameraPosition = CameraPosition.Builder()
        .target(DEFAULT_LOCATION) // Sets the center of the map to Mountain View
        .zoom(17f)            // Sets the zoom
        .bearing(90f)         // Sets the orientation of the camera to east
        .tilt(30f)            // Sets the tilt of the camera to 30 degrees
        .build()              // Creates a CameraPosition from the builder

    val mapView = MapView(LocalContext.current)
    //Composing mapView using AndroidView()
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { mapView.apply {
            this.onCreate(savedInstanceState)
            this.getMapAsync{

              //Adding Marker to a location which is passed by default
                it.addMarker(
                    MarkerOptions().
                    position(DEFAULT_LOCATION)
                        .title("Location Of you")
                )
                //The gestures and zoom features are enabled here
                it.uiSettings.setAllGesturesEnabled(true)
                it.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15f))
                it.animateCamera(CameraUpdateFactory.zoomIn())
                it.animateCamera(CameraUpdateFactory.zoomTo(10f), 2000, null)
                it.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))



            }

           // this helps in loading the map faster on app startup
            this.onResume()


        }

        },
        update = {

            //When the location changes like when clicking the Fab,the new location is updated in the map
            it.getMapAsync{
                it.animateCamera(CameraUpdateFactory.zoomIn())
                it.animateCamera(CameraUpdateFactory.zoomTo(10f), 2000, null)
                it.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15f))
                it.addMarker(
                    MarkerOptions().
                    position(DEFAULT_LOCATION)
                        .title("Location Of you")
                )
            }

        }
    )



}
@Composable
fun SearchText(modifier: Modifier = Modifier,searchText : String,onSearchTextChange: (String) -> Unit)
{
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChange,
                modifier
            .fillMaxHeight(0.1f)
            .fillMaxWidth()
            .padding(4.dp),
}