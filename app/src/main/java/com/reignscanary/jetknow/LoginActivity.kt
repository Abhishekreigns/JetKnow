package com.reignscanary.jetknow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.google.android.gms.maps.model.LatLng
import com.reignscanary.jetknow.composables.LoginPage
import com.reignscanary.jetknow.myTheme.JetKnowTheme


class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val latlng: LatLng? = intent.extras?.getParcelable("latlng")
        setContent {
            JetKnowTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LoginPage(latlng)
                }
            }
        }
    }
}

