package com.reignscanary.jetknow.composables

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.LatLng
import com.reignscanary.jetknow.MainScreenViewModel
import com.reignscanary.jetknow.locationManager


@SuppressLint("MissingPermission")
@Composable
fun HostOfComposables(mainScreenViewModel: MainScreenViewModel = viewModel(),location:Location ,savedInstanceState : Bundle?)
{

    val latLng: LatLng by mainScreenViewModel.latLng.observeAsState(LatLng(-33.88, 151.21))
    val searchText: String by mainScreenViewModel.searchText.observeAsState("")
    val context = LocalContext.current

    Scaffold (
        topBar = {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
            SearchText(
                searchText = searchText,
                onSearchTextChange = { mainScreenViewModel.onSearchTextChange(it) })

                CategoriesCarousel(
                    modifier =
                    Modifier
                        .requiredSize(100.dp)
                        .clip(MaterialTheme.shapes.large)
                        .padding(8.dp)
                )
            }},
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(context, "Enable GPS", Toast.LENGTH_SHORT).show()
                } else {
                    //on Fab Click update the position value to the current location of the user
                        mainScreenViewModel.onLatLngUpdate(
                        LatLng(
                            location.latitude,
                            location.longitude))

                }
            },backgroundColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.onSecondary

            ){

                Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "CurrentLocation")
            }
        },floatingActionButtonPosition = FabPosition.End
    ,bottomBar = { CustomBottomBar() }

    ){
Column {


    CustomMapView(
        DEFAULT_LOCATION = latLng,
        savedInstanceState = savedInstanceState,
        modifier = Modifier
            .padding(top = 6.dp, start = 16.dp, end = 16.dp, bottom = 10.dp)
            .shadow(elevation = 8.dp, shape = MaterialTheme.shapes.large)
            .clip(MaterialTheme.shapes.large)
    )



}
        //Align all the composables in a column
    }
}
@Composable
fun CustomBottomBar() {

    val topRoundedCards = RoundedCornerShape(topStart = 12.dp,topEnd = 12.dp)
BottomNavigation(modifier = Modifier.clip(topRoundedCards),backgroundColor = MaterialTheme.colors.secondary,contentColor = MaterialTheme.colors.onSecondary,content =


{
BottomNavigationItem(selected = false, onClick = { /*TODO*/ },icon = {
    Icon(imageVector = Icons.Filled.Place, contentDescription = "Add" )

},label = {
    Text(text = "Place")
})


    BottomNavigationItem(selected = true, onClick = { /*TODO*/ },icon = {
        Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit" )

    },label = {
        Text(text = "Edit")
    })


    BottomNavigationItem(selected = false, onClick = { /*TODO*/ },icon = {
        Icon(imageVector = Icons.Filled.AccountBox, contentDescription = "Acount" )

    },label = {
        Text(text = "Account")
    })
})
    
}
