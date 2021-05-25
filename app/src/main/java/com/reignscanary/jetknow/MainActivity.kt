package com.reignscanary.jetknow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.android.gms.maps.model.LatLng
import com.reignscanary.jetknow.composables.CustomMapView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val DEFAULT_LOCATION = LatLng(35.7676325, 51.3192201)
            CustomMapView(DEFAULT_LOCATION)

            }
        }
    }


