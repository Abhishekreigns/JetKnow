package com.reignscanary.jetknow.composables

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.SharedPreferences
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.reignscanary.jetknow.backend.MainScreenViewModel
import com.reignscanary.jetknow.activities.locationManager
import com.reignscanary.jetknow.backend.zoomToCurrentLocation
import java.lang.Exception
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import com.reignscanary.jetknow.activities.MainActivity


var fusedLocation:Location? =null
var   gpsLocation : Location? = null


@SuppressLint("MissingPermission")
@Composable
fun HostOfComposables(
    mainScreenViewModel: MainScreenViewModel = viewModel(),
    savedInstanceState: Bundle?,
cachedLocation : SharedPreferences)
{    //Getting cached location,if no location cached or new user,default values will be used
    val lat: String? = cachedLocation.getString("LOCATION_LAT", "20.8021")
    val lon: String? = cachedLocation.getString("LOCATION_LON", "78.24813")
    val state  = rememberScaffoldState()

    val latLng: LatLng by mainScreenViewModel.latLng.observeAsState(LatLng(lat.toString().toDouble(), lon.toString().toDouble()))
    val searchText: String by mainScreenViewModel.searchText.observeAsState("Search")
    val context = LocalContext.current
    val isLoading by mainScreenViewModel.isLoading.observeAsState(false)
    val selectedCategory: String by mainScreenViewModel.selectedCategory.observeAsState("")
    val openDialog by mainScreenViewModel.dialogStatus.observeAsState(initial = false)
    val infoDialog by mainScreenViewModel.infoDialog.observeAsState(initial = false)
    val contributeLatLng by mainScreenViewModel.contributeLatLng.observeAsState(LatLng(0.0, 0.0))
    val isLocationButtonClicked by mainScreenViewModel.onLocationFabClick.observeAsState(false)
    val mapView = MapView(context)
    //To check if the app status
     var appJustGotOpened = true
    Scaffold(
        topBar={
        Column(
            modifier = Modifier
            .padding(10.dp
            )) {
                SearchText(
                    searchText = searchText,
                    onSearchTextChange = { mainScreenViewModel.onSearchTextChange(it) })

                    CategoriesCarousel(
                        modifier =
                        Modifier
                            .requiredSize(100.dp)
                            .clip(MaterialTheme.shapes.large)
                            .padding(8.dp)
                    ) } },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                gotoLocation(context,mainScreenViewModel,cachedLocation)
                                           },backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary

            ){

                Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "CurrentLocation")
            }
        },floatingActionButtonPosition = FabPosition.End,
      bottomBar = { CustomBottomBar() },
        scaffoldState = state,
        drawerBackgroundColor = MaterialTheme.colors.surface,

    ){
        Box(contentAlignment = Alignment.Center) {
            val cameraPosition = CameraPosition.Builder()
                .target(
                    if(appJustGotOpened)
                {//To update the cached location to the ViewModel
                    mainScreenViewModel.onLatLngUpdate(
                        LatLng(
                            lat.toString().toDouble(),
                            lon.toString().toDouble()
                        )
                    )
                    //To load the map to zoom to cached location on app restart
                    LatLng(lat.toString().toDouble(), lon.toString().toDouble())
                }
                else
                    //To dynamically change the map to zoom to current location
                    latLng
                ) // Sets the center of the map to Mountain View
                 .zoom(17f)            // Sets the zoom
                  .bearing(90f)         // Sets the orientation of the camera to east
                   .tilt(45f)            // Sets the tilt of the camera to 30 degrees
                    .build()              // Creates a CameraPosition from the builder
            CustomMapView(
        DEFAULT_LOCATION =

        if(appJustGotOpened)
        {      appJustGotOpened = false
            //To update the cached location to the ViewModel
            mainScreenViewModel.onLatLngUpdate(
                LatLng(
                    lat.toString().toDouble(),
                    lon.toString().toDouble()
                )
            )
            //To place the marker to cached location on app restart
            LatLng(lat.toString().toDouble(), lon.toString().toDouble())

            }
        else
            //To dynamically place the marker on location change
            latLng
                ,
        savedInstanceState = savedInstanceState,
        modifier = Modifier
            .padding(top = 6.dp, start = 16.dp, end = 16.dp, bottom = 10.dp)
            .shadow(elevation = 4.dp, MaterialTheme.shapes.large)
            .clip(MaterialTheme.shapes.large),
        mapView = mapView
    )

    LoadingIndicator(isLoading =isLoading)
    // To zoom to current location when user clicks the Fab(mainly to prevent recomposition)
    if(isLocationButtonClicked){
        mapView.getMapAsync {
        zoomToCurrentLocation(it,cameraPosition)
        mainScreenViewModel.onLocationClicked(false)

    }}
    if (infoDialog) {
        InfoPopup(mainScreenViewModel, selectedCategory, selectedMarker)
    }
    if (openDialog) {
        AlertDialogComponent(context = context, mainScreenViewModel, contributeLatLng)
    }

}
   }
}

@SuppressLint("MissingPermission")
fun gotoLocation(context : Context, mainScreenViewModel : MainScreenViewModel,cachedLocation: SharedPreferences) {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        enableGps(context)
        Toast.makeText(context, "Enable GPS", Toast.LENGTH_SHORT).show()


    } else {
        //on Fab Click update the position value to the current location of the user
        mainScreenViewModel.onLocationClicked(true)
        try {
            val locationListener: LocationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    println("DEBUG 1")
                    gpsLocation = location
                }

                override fun onProviderEnabled(provider: String) {
                    println("DEBUG 2")
                    Toast.makeText(context, "Great!!", Toast.LENGTH_LONG).show()
                }

                override fun onProviderDisabled(provider: String) {
                    println("DEBUG 3")
                    Toast.makeText(context, "onProviderDisabled", Toast.LENGTH_LONG)
                        .show()
                }

                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
                    println("DEBUG 4")

                }
            }
            fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(context)
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                fusedLocation = it
            }
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000L,
                100f, locationListener
            )
        } catch (e: Exception) {
            Toast.makeText(context, "${e.stackTrace}", Toast.LENGTH_SHORT).show()
        }


        if (gpsLocation != null) {
            mainScreenViewModel.onLatLngUpdate(
                LatLng(
                    gpsLocation!!.latitude,
                    gpsLocation!!.longitude
                )
            )
            //To update the latest user location to the sharedpreferences with GPS
            cachedLocation.edit().putString("LOCATION_LAT",
               gpsLocation!!.latitude.toString()
           ).apply()
            cachedLocation.edit().putString("LOCATION_LON", gpsLocation!!.longitude.toString()
            ).apply()
            cachedLocation.edit().putString("LOCATION_PROVIDER", gpsLocation!!.provider.toString()).apply()

           Toast(context).cancel()
                Toast.makeText(context,"Loading.....", Toast.LENGTH_LONG).show()
        Toast.makeText(context,"${cachedLocation.getString("LOCATION_LAT", "20.8021")}",Toast.LENGTH_SHORT).show()
        }
        else{

            fusedLocation?.let {
                Toast.makeText(context,"Using Approximate location,GPS signal is low", Toast.LENGTH_LONG).show()
                //To update the latest user location to the sharedpreferences with fusedLocation Client
                cachedLocation.edit().putString("LOCATION_LAT",it.latitude.toString()
                ).apply()
                cachedLocation.edit().putString("LOCATION_LON", it.longitude.toString()
                ).apply()
                cachedLocation.edit().putString("LOCATION_PROVIDER", it.provider.toString()).apply()
                LatLng(
                    it.latitude,
                    it.longitude
                )
            }?.let {

                mainScreenViewModel.onLatLngUpdate(it)



            }


        }}
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
        Icon(imageVector = Icons.Filled.AccountBox, contentDescription = "Account" )

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
           task.getResult(ApiException::class.java)

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
                    Toast.makeText(context,"If you give the permission,it would be easier to find the locations", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}
