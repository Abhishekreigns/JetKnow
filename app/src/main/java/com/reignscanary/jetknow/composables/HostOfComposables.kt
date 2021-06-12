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
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.reignscanary.jetknow.MainScreenViewModel
import com.reignscanary.jetknow.locationManager
import java.lang.Exception

var fusedLocation:Location? =null
var   gpsLocation : Location? = null
@SuppressLint("StaticFieldLeak")
lateinit var fusedLocationProviderClient: FusedLocationProviderClient
var i =0
@SuppressLint("MissingPermission")
@Composable
fun HostOfComposables(
    mainScreenViewModel: MainScreenViewModel = viewModel(),
    savedInstanceState: Bundle?)
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
                    enableGps(context)
                    Toast.makeText(context, "Enable GPS", Toast.LENGTH_SHORT).show()


                } else {
                    //on Fab Click update the position value to the current location of the user
                         try{
                            fusedLocationProviderClient =
                                LocationServices.getFusedLocationProviderClient(context)
                            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                                fusedLocation = it
                            }
                             locationManager.requestLocationUpdates(
        LocationManager.GPS_PROVIDER,
        1000L,
        100f,
        object :LocationListener{

            override fun onLocationChanged(p0: Location?) {
              if(p0!=null){
                  gpsLocation = p0
              }
            }

            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

            }

            override fun onProviderEnabled(p0: String?) {

            }

            override fun onProviderDisabled(p0: String?) {

            }

        }
                             )
                         }
catch (e : Exception){

    Toast.makeText(context,"${e.stackTrace}",Toast.LENGTH_SHORT).show()
}


                    if(gpsLocation!=null) {
                        mainScreenViewModel.onLatLngUpdate(
                            LatLng(
                                gpsLocation!!.latitude,
                                gpsLocation!!.longitude
                            )
                        )

                        if(i <= 1)
                        { Toast.makeText(context,"Loading.....", Toast.LENGTH_LONG).show()}
                        i++
                    }
                    else {

                        if(i==0) {
                            Toast.makeText(
                                context,
                                "GPS signal is low!!,using approximate location",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else{
                            Toast.makeText(
                                context,
                                "Click Again to get an accurate location",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        fusedLocation?.let {
                            LatLng(
                                it.latitude,
                                it.longitude
                            )
                        }?.let { mainScreenViewModel.onLatLngUpdate(it) }
                        i++

                    }
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



fun enableGps(context: Context) {

    val locationRequest = LocationRequest.create()
    locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

    val result: Task<LocationSettingsResponse> = LocationServices.getSettingsClient(context).checkLocationSettings(builder.build())

    result.addOnCompleteListener {
        task->
        try {
            val response = task.getResult(ApiException::class.java)


        }
        catch (exception: ApiException)
        {
            when (exception.statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    try {
                        val resolvable: ResolvableApiException = exception as ResolvableApiException
                        resolvable.startResolutionForResult(
                            context as Activity, LocationRequest.PRIORITY_HIGH_ACCURACY
                        )
                    } catch (e: IntentSender.SendIntentException) {
                        // Ignore the error.
                    } catch (e: ClassCastException) {
                        // Ignore, should be an impossible error.
                    }
                }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    Toast.makeText(context,"If you give only,it will work good", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}
