package com.reignscanary.jetknow.composables

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.reignscanary.jetknow.ContributeActivity
import com.reignscanary.jetknow.Contributions
import com.reignscanary.jetknow.MainScreenViewModel
import com.reignscanary.jetknow.R

@Composable
fun CustomMapView(
    DEFAULT_LOCATION: LatLng,
    savedInstanceState: Bundle?,
    modifier: Modifier = Modifier,
    onLatLngUpdate: (LatLng) -> Unit
)
{

      val mainViewModel : MainScreenViewModel= viewModel()
    val context  = LocalContext.current
    val cameraPosition = CameraPosition.Builder()
        .target(DEFAULT_LOCATION) // Sets the center of the map to Mountain View
        .zoom(17f)            // Sets the zoom
        .bearing(90f)         // Sets the orientation of the camera to east
        .tilt(30f)            // Sets the tilt of the camera to 30 degrees
        .build()              // Creates a CameraPosition from the builder
val openDialog  by mainViewModel.dialogStatus.observeAsState(initial = false)
    val contributeLatLng by mainViewModel.contributeLatLng.observeAsState(LatLng(0.0,0.0))
    val mapView = MapView(context)

  //  val wantToContribute by mainViewModel.wantToContribute.observeAsState(initial = false)
    //Composing mapView using AndroidView()

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { mapView.apply {
            this.onCreate(savedInstanceState)
            this.getMapAsync{
                it.mapType = GoogleMap.MAP_TYPE_SATELLITE
                //The gestures and zoom features are enabled here
                it.uiSettings.setAllGesturesEnabled(true)
                it.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15f))
                it.animateCamera(CameraUpdateFactory.zoomIn())
                it.animateCamera(CameraUpdateFactory.zoomTo(10f), 2000, null)
                 it.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

               it.setOnMapLongClickListener {
                   latlng ->
                 mainViewModel.onDialogStatusChanged(true)
                  mainViewModel.onNewContributeLatLng(latlng)
                   Toast.makeText(context,"$latlng",Toast.LENGTH_SHORT).show()

               }
            }

            // this helps in loading the map faster on app startup
            this.onResume()


        }

        },
        update = {


            //When the location changes like when clicking the Fab,the new location is updated in the map
            it.getMapAsync{

                it.apply {
                    mapType = GoogleMap.MAP_TYPE_NORMAL
                animateCamera(CameraUpdateFactory.zoomIn())
                animateCamera(CameraUpdateFactory.zoomTo(10f), 2000, null)
               animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

               addMarker(
                    MarkerOptions().
                    position(DEFAULT_LOCATION)
                        .title("Location Of you")
                )
            }}

        }
    )
    if(openDialog) {

        AlertDialogComponent(context = context,mainViewModel,contributeLatLng)

    }



}

