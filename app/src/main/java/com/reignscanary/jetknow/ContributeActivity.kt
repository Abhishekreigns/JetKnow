package com.reignscanary.jetknow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.google.android.gms.maps.model.LatLng
import com.reignscanary.jetknow.composables.ContributeScreenContents
import com.reignscanary.jetknow.composables.Screen
import com.reignscanary.jetknow.myTheme.JetKnowTheme



class ContributeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val latlng: LatLng? = intent.extras?.getParcelable("latlng")

        setContent {



            JetKnowTheme{
                Screen {
                    if (latlng != null) {
                        ContributeScreenContents(latlng)
                    }

                }
            }}
        }
    }



