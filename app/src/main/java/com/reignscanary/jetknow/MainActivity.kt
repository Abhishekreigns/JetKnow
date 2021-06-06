package com.reignscanary.jetknow

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import android.location.*
import android.location.LocationListener
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationToken
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


 val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager


        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            enableGps(applicationContext)

        }
        else
        {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                    location  ->
                setContent {
                    JetKnowTheme {
                        Surface() {
                            Screen {

                                HostOfComposables(
                                    savedInstanceState = savedInstanceState,
                                    location = location
                                )
                            }
                        }
                    }
                }

            } }

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Categories")


    }
    fun enableGps(context: Context) {

        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val result: Task<LocationSettingsResponse> = LocationServices.getSettingsClient(context).checkLocationSettings(builder.build())

        result.addOnCompleteListener { task ->
            try {
                val response = task.getResult(ApiException::class.java)

            }
            catch (exception: ApiException)
            {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        try {
                            val resolvable: ResolvableApiException = exception as ResolvableApiException
                            resolvable.startResolutionForResult(
                                this,LocationRequest.PRIORITY_HIGH_ACCURACY
                            )
                        } catch (e: IntentSender.SendIntentException) {
                            // Ignore the error.
                        } catch (e: ClassCastException) {
                            // Ignore, should be an impossible error.
                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        Toast.makeText(context,"If you give only,it will work good", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        Toast.makeText(applicationContext,"Loading.....", Toast.LENGTH_LONG).show()

    }

    override fun onStart() {
        super.onStart()
        startLocationUpdates()


    }

    override fun onStop() {
        super.onStop()
        stopLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun stopLocationUpdates() {
        val locationListener =  LocationListener{


        }
        locationManager.removeUpdates(locationListener)

    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationListener =  LocationListener{


        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0L,0f,locationListener)

    }

    companion object {

        const val LOCATION_PERMISSION = 100


    }
}












