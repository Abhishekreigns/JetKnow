package com.reignscanary.jetknow.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.LatLng
import com.reignscanary.jetknow.MainScreenViewModel

@Composable
fun ContributeScreenContents(latlng : LatLng)
{
var name : String by remember {
    mutableStateOf("Enter Name")
}
    val mainScreenViewModel : MainScreenViewModel = viewModel()
    val searchText :String by mainScreenViewModel.searchText.observeAsState("" )
    val roundedBox = RoundedCornerShape(10.dp)
   LazyColumn(modifier = Modifier.fillMaxSize().clip(roundedBox).shadow(elevation = 10.dp).padding(top = 100.dp,end = 10.dp,start = 10.dp)) {
       item{

       OutlinedTextField(value = name,onValueChange ={ name = it } ,modifier = Modifier.fillMaxWidth(1f).padding(end = 20.dp,start = 20.dp),singleLine = true)
       OutlinedTextField(value = "$latlng",onValueChange ={},modifier = Modifier.fillMaxWidth(1f).padding(end = 20.dp,start = 20.dp),singleLine = true)
       CategoriesCarousel(modifier = Modifier
           .shadow(elevation = 50.dp, shape = roundedBox)
           .requiredSize(135.dp)
           .clip(roundedBox)
           .padding(8.dp) )
       SearchText(searchText = searchText,modifier = Modifier.padding(8.dp).requiredHeight(75.dp) ) {
           mainScreenViewModel.onSearchTextChange(it)
       }

   }}

}