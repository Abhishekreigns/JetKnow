package com.reignscanary.jetknow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.LatLng
import com.reignscanary.jetknow.composables.CustomMapView
import com.reignscanary.jetknow.composables.HostOfComposables
import com.reignscanary.jetknow.composables.MainScreenViewModel
import com.reignscanary.jetknow.composables.SearchText

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

           HostOfComposables()


            }
        }
    }


