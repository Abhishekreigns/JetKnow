package com.reignscanary.jetknow.activities


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.app.ActivityCompat
import com.reignscanary.jetknow.composables.SplashScreen
import com.reignscanary.jetknow.ui.theme.JetKnowTheme
private var TIME_OUT:Long = 1000



class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadSplashScreen()
        setContent {
            JetKnowTheme {

                Surface(color = MaterialTheme.colors.background) {
                    SplashScreen()
                }
            }
        }
    }
    private fun loadSplashScreen(){
        Handler().postDelayed({
            // Launching MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, TIME_OUT)
    }
    companion object {

        const val LOCATION_PERMISSION = 100
        const val RC_SIGN_IN = 1

    }
}

