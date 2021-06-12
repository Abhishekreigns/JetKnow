package com.reignscanary.jetknow

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.core.app.ActivityCompat
import com.reignscanary.jetknow.composables.HostOfComposables
import com.reignscanary.jetknow.composables.Screen
import com.reignscanary.jetknow.myTheme.JetKnowTheme

lateinit var locationManager: LocationManager
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        setContent {
                    JetKnowTheme {
                        Surface {
                            Screen {
                                HostOfComposables(
                                            savedInstanceState = savedInstanceState
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












