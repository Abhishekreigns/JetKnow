package com.reignscanary.jetknow.composables



import android.location.Location
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
@Composable
fun HostOfComposables(mainScreenViewModel: MainScreenViewModel = viewModel(),location: Location,savedInstanceState : Bundle?)
{
    val latLng  : LatLng by mainScreenViewModel.latLng.observeAsState(LatLng(-33.88,151.21))
    val searchText :String by mainScreenViewModel.searchText.observeAsState("" )
    val roundedBox = RoundedCornerShape(12.dp)


    //Align all the composables in a column
   Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(1f)
        )

    {
        SearchText(searchText = searchText,
            modifier = Modifier
                .fillMaxWidth(0.9f)
        ) {
            mainScreenViewModel.onSearchTextChange(it)
        }
        CategoriesCarousel(modifier =  Modifier
            .shadow(elevation =20.dp,shape = roundedBox)
            .requiredSize(150.dp)
            .padding(10.dp)
            .clip(roundedBox)

        )

        Spacer(modifier = Modifier.padding(10.dp))

        CustomMapView(
            DEFAULT_LOCATION = latLng,
            savedInstanceState = savedInstanceState,
            modifier = Modifier
                .weight(16f)
                .padding(top = 6.dp)
                .shadow(elevation = 8.dp,shape = roundedBox)
                .clip(roundedBox)


        ){
            mainScreenViewModel.onLatLngUpdate(it)
        }
        Spacer(modifier = Modifier.padding(2.dp))

        FloatingActionButton(onClick = {
            //on Fab Click update the position value to the current location of the user
            mainScreenViewModel.onLatLngUpdate(LatLng(location.latitude, location.longitude))
        }
        ) {}

    }
}



@Composable
fun CategoriesCarousel(modifier: Modifier)
{ // val scrollState = rememberScrollState()
    LazyRow(modifier = Modifier
        .background(Color.White)

        .padding(top= 10.dp)
        .clip(RoundedCornerShape(10.dp))


    ) {
item {


for(i in 1..50 ){

    Card(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(enabled = true,onClickLabel = "HI",onClick = {/*TODO*/})) {
        Text(text = "Hi There",modifier= modifier.background(Color.White),textAlign = TextAlign.Center)

    }
}


}

    }


}


@Composable
fun CustomMapView(
    DEFAULT_LOCATION: LatLng,
    savedInstanceState: Bundle?,
    modifier: Modifier = Modifier,
    onLatLngUpdate: (LatLng) -> Unit
)
{


    val cameraPosition = CameraPosition.Builder()
        .target(DEFAULT_LOCATION) // Sets the center of the map to Mountain View
        .zoom(17f)            // Sets the zoom
        .bearing(90f)         // Sets the orientation of the camera to east
        .tilt(30f)            // Sets the tilt of the camera to 30 degrees
        .build()              // Creates a CameraPosition from the builder

    val mapView = MapView(LocalContext.current)
    //Composing mapView using AndroidView()
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { mapView.apply {
            this.onCreate(savedInstanceState)
            this.getMapAsync{

                //The gestures and zoom features are enabled here
                it.uiSettings.setAllGesturesEnabled(true)
                it.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15f))
                it.animateCamera(CameraUpdateFactory.zoomIn())
                it.animateCamera(CameraUpdateFactory.zoomTo(10f), 2000, null)
                it.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))



            }

           // this helps in loading the map faster on app startup
            this.onResume()


        }

        },
        update = {

            //When the location changes like when clicking the Fab,the new location is updated in the map
            it.getMapAsync{
                it.animateCamera(CameraUpdateFactory.zoomIn())
                it.animateCamera(CameraUpdateFactory.zoomTo(10f), 2000, null)
                it.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15f))
                it.addMarker(
                    MarkerOptions().
                    position(DEFAULT_LOCATION)
                        .title("Location Of you")
                )
            }

        }
    )



}
@Composable
fun SearchText(modifier: Modifier = Modifier,searchText : String,onSearchTextChange: (String) -> Unit)
{
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        modifier
            .fillMaxHeight(0.1f)
            .fillMaxWidth()
            .padding(4.dp))
}