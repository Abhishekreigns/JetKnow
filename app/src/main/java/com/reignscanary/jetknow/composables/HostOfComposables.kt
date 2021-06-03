package com.reignscanary.jetknow.composables

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.reignscanary.jetknow.MainActivity
import com.reignscanary.jetknow.MainScreenViewModel
import com.reignscanary.jetknow.locationManager


@SuppressLint("MissingPermission")
@Composable
fun HostOfComposables(mainScreenViewModel: MainScreenViewModel = viewModel(),location:Location ,savedInstanceState : Bundle?)
{
    val latLng  : LatLng by mainScreenViewModel.latLng.observeAsState(LatLng(-33.88,151.21))
    val searchText :String by mainScreenViewModel.searchText.observeAsState("" )
    val roundedBox = RoundedCornerShape(12.dp)
    val context = LocalContext.current

    //Align all the composables in a column
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .padding(10.dp)
    ) {
        SearchText(searchText = searchText,onSearchTextChange = {mainScreenViewModel.onSearchTextChange(it)})



        CategoriesCarousel(
            modifier =
            Modifier
                .shadow(elevation = 50.dp, shape = roundedBox)
                .requiredSize(135.dp)
                .clip(roundedBox)
                .padding(8.dp)



        )



        Spacer(modifier = Modifier.padding(10.dp))

        CustomMapView(
            DEFAULT_LOCATION = latLng,
            savedInstanceState = savedInstanceState,
            modifier = Modifier
                .weight(16f)
                .padding(top = 6.dp)
                .shadow(elevation = 8.dp, shape = roundedBox)
                .clip(roundedBox)


        ){
            mainScreenViewModel.onLatLngUpdate(it)
        }
        Spacer(modifier = Modifier.padding(2.dp))

        FloatingActionButton(onClick = {


            if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                  Toast.makeText(context,"Enable GPS",Toast.LENGTH_SHORT).show()


            }
            else {
                //on Fab Click update the position value to the current location of the user

                mainScreenViewModel.onLatLngUpdate(LatLng(location.latitude, location.longitude))





}





        }
        ) {

        }

    }
}
