package com.reignscanary.jetknow

import com.google.android.gms.maps.model.LatLng

data class Contributions(

    val latlng : LatLng,

    val name : String = "Enter Name",
    val category: Category  = Category("")



)