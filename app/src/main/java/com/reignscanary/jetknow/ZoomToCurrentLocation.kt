package com.reignscanary.jetknow

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition


fun zoomToCurrentLocation(googleMap : GoogleMap,cameraPosition: CameraPosition) {


    googleMap.apply{

        this.animateCamera(CameraUpdateFactory.zoomIn())
        this.animateCamera(CameraUpdateFactory.zoomTo(10f), 2000, null)
        this.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}