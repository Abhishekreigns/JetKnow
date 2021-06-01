package com.reignscanary.jetknow.composables

import android.os.Bundle
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

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

            onLatLngUpdate(DEFAULT_LOCATION)

            //When the location changes like when clicking the Fab,the new location is updated in the map
            it.getMapAsync{

                it.apply {
                moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15f))
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



}