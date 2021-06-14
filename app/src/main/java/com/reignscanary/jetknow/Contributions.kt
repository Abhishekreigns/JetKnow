package com.reignscanary.jetknow

import com.google.android.gms.maps.model.LatLng

data class Contributions(
       val id :String,
    val lat : Double,
       val lng:Double,
       val name : String = "Enter Name"
)