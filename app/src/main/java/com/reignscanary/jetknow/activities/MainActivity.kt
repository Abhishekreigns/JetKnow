package com.reignscanary.jetknow.activities


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.core.app.ActivityCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.reignscanary.jetknow.R
import com.reignscanary.jetknow.backend.checkLocationPermission
import com.reignscanary.jetknow.composables.HostOfComposables
import com.reignscanary.jetknow.composables.Screen
import com.reignscanary.jetknow.myTheme.JetKnowTheme



lateinit var locationManager: LocationManager

class MainActivity : ComponentActivity() {

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
                    Toast.makeText(this,"Welcome back ${user.displayName}!!",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            //Creating the sharedPreferences in respect to the application context
        val sharedPreferences : SharedPreferences by lazy{ applicationContext.getSharedPreferences("LatLng", MODE_PRIVATE)}
        checkLocationPermission(this)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        setContent {
                    JetKnowTheme {
                        Surface {
                            Screen {
                                HostOfComposables(savedInstanceState = savedInstanceState,cachedLocation = sharedPreferences)
                            }
                        }
                    }
        }

    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val intent = Intent(this, SplashActivity::class.java)
        startActivityForResult(intent, SplashActivity.LOCATION_PERMISSION)
    }



}












