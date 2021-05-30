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
import android.provider.Settings



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //Request location on opening the app for the first time
        ActivityCompat.requestPermissions( this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION)

        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //check if GPS is on
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            turnGpsON()
        }
        else{
                //Check if location permission granted
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this@MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                //if not granted ask the permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION
                )
            } else {
                //if granted assign the location to pass it to the hostOfComposable
                val locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (locationGPS != null) {

                    setContent {

                        HostOfComposables(
                            savedInstanceState = savedInstanceState,
                            location = locationGPS

                        )
                    }


                } else {

                    //If location data is not retrieved mostly due to network or compass error show the toast
                    Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this,"\nIf this happens often,its better to open google maps and calibrate the issue",Toast.LENGTH_SHORT).show()


                }
            }
        }


    }
//Method/Function to turn on GPS
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
