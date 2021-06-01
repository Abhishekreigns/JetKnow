package com.reignscanary.jetknow.composables

import android.app.Activity
import android.location.Location
import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.LatLng
import com.reignscanary.jetknow.MainActivity
import com.reignscanary.jetknow.MainScreenViewModel


@Composable
fun HostOfComposables(mainScreenViewModel: MainScreenViewModel = viewModel(), location: Location, savedInstanceState : Bundle?)
{
    val latLng  : LatLng by mainScreenViewModel.latLng.observeAsState(LatLng(-33.88,151.21))
    val searchText :String by mainScreenViewModel.searchText.observeAsState("" )
    val roundedBox = RoundedCornerShape(12.dp)


    //Align all the composables in a column
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(1f)
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
            //on Fab Click update the position value to the current location of the user
            mainScreenViewModel.onLatLngUpdate(LatLng(location.latitude, location.longitude))
        }
        ) {

        }

    }
}
