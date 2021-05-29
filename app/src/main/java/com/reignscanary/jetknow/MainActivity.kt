package com.reignscanary.jetknow

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import android.app.AlertDialog

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import com.reignscanary.jetknow.composables.HostOfComposables


import android.content.Intent
import android.location.Location
import android.provider.Settings
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import kotlin.math.ln


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions( this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION)
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            turnGpsON()
        }
        else{

            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this@MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION
                )
            } else {
                val locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (locationGPS != null) {
                    val lat = locationGPS.latitude
                    val longi = locationGPS.longitude
                  // Toast.makeText(this,"Your Location: \nLatitude: $lat\nLongitude: $longi",Toast.LENGTH_SHORT).show()
                    setContent {

                        HostOfComposables(
                            savedInstanceState = savedInstanceState,
                            location = locationGPS

                        )
                    }


                } else {Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this,"\nIf this happens often,its better to open google maps and calibrate the issue",Toast.LENGTH_SHORT).show()


                }
            }
        }


    }

    private fun turnGpsON() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes"
        ) { _, _ -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            .setNegativeButton("No"
            ) { dialog, _ -> dialog.cancel() }
        builder.create()?.show()
    }

    companion object {

            const val LOCATION_PERMISSION = 100


    }

}
