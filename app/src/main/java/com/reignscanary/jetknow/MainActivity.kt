package com.reignscanary.jetknow

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.SideEffect
import androidx.core.app.ActivityCompat
import com.reignscanary.jetknow.composables.HostOfComposables
import com.reignscanary.jetknow.ui.theme.JetKnowTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

var locationManager : LocationManager = getSystemService(LOCATION_SERVICE) as LocationManager
setContent {

    HostOfComposables(
        savedInstanceState = savedInstanceState,
        locationManager = locationManager

    )


} }

    companion object{

        const val LOCATION_PERMISSION = 100
    }

    }


