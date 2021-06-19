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
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.reignscanary.jetknow.MainScreenViewModel
import com.reignscanary.jetknow.locationManager
import com.reignscanary.jetknow.zoomToCurrentLocation
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

    val latLng: LatLng by mainScreenViewModel.latLng.observeAsState(LatLng(20.8021, 78.24813))
    val searchText: String by mainScreenViewModel.searchText.observeAsState("Search")
    val context = LocalContext.current
    val isLoading by mainScreenViewModel.isLoading.observeAsState(false)
    val selectedCategory: String by mainScreenViewModel.selectedCategory.observeAsState("")
    val openDialog by mainScreenViewModel.dialogStatus.observeAsState(initial = false)
    val infoDialog by mainScreenViewModel.infoDialog.observeAsState(initial = false)
    val contributeLatLng by mainScreenViewModel.contributeLatLng.observeAsState(LatLng(0.0, 0.0))
    val isLocationButtonClicked by mainScreenViewModel.onLocationFabClick.observeAsState(false)
    val mapView = MapView(context)

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
                gotoLocation(context,mainScreenViewModel)
                                           },backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary

            ){

                Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "CurrentLocation")
            }
        },floatingActionButtonPosition = FabPosition.End
    ,bottomBar = { CustomBottomBar() }

    ){
Box(contentAlignment = Alignment.Center) {

    val cameraPosition = CameraPosition.Builder()
        .target(latLng) // Sets the center of the map to Mountain View
        .zoom(17f)            // Sets the zoom
        .bearing(90f)         // Sets the orientation of the camera to east
        .tilt(45f)            // Sets the tilt of the camera to 30 degrees
        .build()              // Creates a CameraPosition from the builder
    CustomMapView(
        DEFAULT_LOCATION = latLng,
        savedInstanceState = savedInstanceState,
        modifier = Modifier
            .padding(top = 6.dp, start = 16.dp, end = 16.dp, bottom = 10.dp)
            .shadow(elevation = 4.dp,MaterialTheme.shapes.large)
            .clip(MaterialTheme.shapes.large),
        mapView = mapView
    )
    LoadingIndicator(isLoading =isLoading)

    // To zoom to current location when user clicks the Fab(mainly to prevent recomposition)
    if(isLocationButtonClicked){
        mapView.getMapAsync {
        zoomToCurrentLocation(it,cameraPosition)
        Toast.makeText(context,"Whoeiii !!",Toast.LENGTH_SHORT).show()
        mainScreenViewModel.onLocationClicked(false)

    }}

    if (infoDialog) {

        InfoPopup(mainScreenViewModel, selectedCategory, selectedMarker)
    }


    if (openDialog) {

        AlertDialogComponent(context = context, mainScreenViewModel, contributeLatLng)

    }



}
        //Align all the composables in a column
    }
}

@SuppressLint("MissingPermission")
fun gotoLocation(context : Context, mainScreenViewModel : MainScreenViewModel) {

    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        enableGps(context)
        Toast.makeText(context, "Enable GPS", Toast.LENGTH_SHORT).show()


    } else {
        //on Fab Click update the position value to the current location of the user
        mainScreenViewModel.onLocationClicked(true)
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

            if(i <= 2)
            { Toast(context).cancel()
                Toast.makeText(context,"Loading.....", Toast.LENGTH_LONG).show()}
            i++
        }
        else {

            if(i==1) {
                Toast(context).cancel()
                Toast.makeText(
                    context,
                    "GPS signal is low!!,using approximate location",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else{
                Toast(context).cancel()
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




















}

@Composable
fun CustomBottomBar() {

    val topRoundedCards = RoundedCornerShape(topStart = 12.dp,topEnd = 12.dp)
BottomNavigation(modifier = Modifier.clip(topRoundedCards),backgroundColor = MaterialTheme.colors.surface,contentColor = MaterialTheme.colors.onSurface,content =


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
