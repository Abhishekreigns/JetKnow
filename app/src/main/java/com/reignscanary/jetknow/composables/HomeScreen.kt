package com.reignscanary.jetknow.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@Composable
fun CustomMapView(DEFAULT_LOCATION: LatLng)
{

 AndroidView(
        modifier = Modifier.fillMaxSize(),
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