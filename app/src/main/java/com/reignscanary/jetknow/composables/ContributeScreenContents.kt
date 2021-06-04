package com.reignscanary.jetknow.composables

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.LatLng
import com.reignscanary.jetknow.MainScreenViewModel
import com.reignscanary.jetknow.R

@Composable
fun ContributeScreenContents(latlng : LatLng)
{
var name : String by remember {
    mutableStateOf("")
}
    val context = LocalContext.current
    val mainScreenViewModel : MainScreenViewModel = viewModel()
    val searchText :String by mainScreenViewModel.searchText.observeAsState("" )
    val roundedBox = RoundedCornerShape(10.dp)
   LazyColumn(modifier = Modifier
       .fillMaxSize()
       .clip(roundedBox)
       .padding(top = 150.dp, end = 10.dp, start = 10.dp)) {
       item{


           Text(
               text ="Add Details here",
               style = TextStyle(fontSize = 30.sp,fontFamily = FontFamily(Font(R.font.opensansbold))),
               modifier = Modifier.padding(start = 20.dp,end = 10.dp,bottom = 10.dp),
               textAlign = TextAlign.Center
           )
           TextField(
               value = name,
           onValueChange ={ name = it },
           modifier = Modifier
               .fillMaxWidth(1f)
               .padding(top = 10.dp,end = 20.dp, start = 20.dp)
               .clip(RoundedCornerShape(12.dp))
               ,
           placeholder = { Text(text = "Enter Name")}
           ,singleLine = true
           )
           TextField(
               value = "$latlng",onValueChange ={},modifier = Modifier
                   .fillMaxWidth(1f)
                   .padding(top = 16.dp, end = 20.dp, start = 20.dp)
                   .clip(RoundedCornerShape(12.dp)),
               singleLine = true
           )
           Text(
               text ="Category",
               style = TextStyle(fontSize = 20.sp,fontFamily = FontFamily(Font(R.font.opnsasnsemibold))),
               modifier = Modifier.padding(top=10.dp,start = 20.dp,end = 20.dp),
               textAlign = TextAlign.Center
           )
           CategoriesCarousel(modifier = Modifier
               .shadow(elevation = 50.dp, shape = roundedBox)
               .requiredSize(135.dp)
               .clip(roundedBox)
               .padding(8.dp) )
           SearchText(searchText = searchText,modifier = Modifier
               .padding(8.dp)
               .requiredHeight(75.dp) ) {
           mainScreenViewModel.onSearchTextChange(it)
       }
         Box(contentAlignment = Alignment.BottomEnd,modifier = Modifier
             .fillMaxSize(1f)
             .padding(10.dp)) {


             Button(
                 onClick = {
                     updateDetails()
                     Toast.makeText(context,"Updated",Toast.LENGTH_SHORT).show()
                           },
                 modifier = Modifier.fillMaxWidth(0.30f)) {
                 Text(text = "Done")
                 Icon(Icons.Filled.Done,
                     contentDescription = "DoneButton")

         }}


   }}

}

fun updateDetails() {
    TODO("Not yet implemented")
}
