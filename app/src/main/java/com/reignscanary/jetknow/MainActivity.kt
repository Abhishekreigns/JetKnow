package com.reignscanary.jetknow

import android.Manifest
import android.content.Context
import android.content.IntentSender
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import android.location.*
import androidx.compose.material.Surface
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.reignscanary.jetknow.composables.HostOfComposables
import com.reignscanary.jetknow.composables.Screen
import com.reignscanary.jetknow.myTheme.JetKnowTheme

lateinit var locationManager: LocationManager
class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
           ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),
               LOCATION_PERMISSION)
            return
        }

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        setContent {
                    JetKnowTheme {
                        Surface() {
                            Screen {


                                        HostOfComposables(
                                            savedInstanceState = savedInstanceState,

                                        )

                            }
                        }
                    }


            }

    }





    companion object {

        const val LOCATION_PERMISSION = 100


    }
}












