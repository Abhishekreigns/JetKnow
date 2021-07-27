package com.reignscanary.jetknow.composables

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.reignscanary.jetknow.R
import com.reignscanary.jetknow.activities.MainActivity
import com.reignscanary.jetknow.backend.Contributions
import com.reignscanary.jetknow.backend.MainScreenViewModel
import com.reignscanary.jetknow.backend.updateDetails

@Composable
fun ContributeScreenContents(latlng : LatLng)
{
    val focusManager = LocalFocusManager.current
var name : String by remember {
    mutableStateOf("")
}
   var phoneNumber : String by remember {
       mutableStateOf("")
   }
    val context = LocalContext.current
    val mainScreenViewModel : MainScreenViewModel = viewModel()
    val searchText :String by mainScreenViewModel.searchText.observeAsState("" )
   Column(modifier = Modifier
       .fillMaxSize()
       .padding(top = 120.dp, end = 10.dp, start = 10.dp)) {

LazyColumn(modifier =  Modifier.weight(0.4f))
{
    item{
    Text(
        text = "Add Details here",
        style = TextStyle(color = MaterialTheme.colors.onSurface,fontSize = 30.sp, fontFamily = FontFamily(Font(R.font.opensansbold))),
        modifier = Modifier.padding(start = 20.dp, end = 10.dp),
    )
        //Name Field
           TextField(
               value = name,
               onValueChange = { name = it },
               modifier = Modifier
                   .fillMaxWidth(1f)
                   .padding(top = 16.dp, end = 20.dp, start = 20.dp)
                   .clip(MaterialTheme.shapes.large), placeholder = {
                   Text(text = "Enter Name")
               },

               singleLine = true,
               keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
               keyboardActions = KeyboardActions(
                   onDone = {
                       focusManager.clearFocus()
                   }
               )
           )
           //LatLng Field
           TextField(
               value = "$latlng", onValueChange = {}, modifier = Modifier
                   .fillMaxWidth(1f)
                   .padding(top = 16.dp, end = 20.dp, start = 20.dp)
                   .clip(MaterialTheme.shapes.large),
               singleLine = true
           )
           //PhoneNumber Field
           TextField(
               value = phoneNumber, onValueChange = {
                   phoneNumber = it
               }, modifier = Modifier
                   .fillMaxWidth(1f)
                   .padding(top = 16.dp, end = 20.dp, start = 20.dp)
                   .clip(MaterialTheme.shapes.large),
               singleLine = true,
               placeholder = {
                   Text(text = "Enter Phone ")
               },
               keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
               keyboardActions = KeyboardActions(
                   onDone = {
                       //to hide the keyboard after user finishes entering the number
                       focusManager.clearFocus()
                   }
               )
           )
           Text(
               text = "Category",
               style = TextStyle(
                   color = MaterialTheme.colors.onSurface,
                   fontSize = 20.sp,
                   fontFamily = FontFamily(Font(R.font.opensansbold))
               ),
               modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp),
               textAlign = TextAlign.Center
           )
           CategoriesCarousel(
               modifier = Modifier
                   .requiredSize(100.dp)
                   .clip(MaterialTheme.shapes.large)
                   .padding(10.dp)
           )
           SearchText(
               searchText = searchText, modifier = Modifier
                   .padding(8.dp)
           ) {
               mainScreenViewModel.onSearchTextChange(it)
           }
           Box(
               contentAlignment = Alignment.BottomEnd, modifier = Modifier
                   .fillMaxSize(1f)
                   .padding(10.dp)
                   .weight(0.6f)
           ) {


               Button(
                   onClick = {
                       updateDetails(context, searchText, name, phoneNumber, latlng)

                   },
                   modifier = Modifier.fillMaxWidth(0.30f)
               ) {
                   Text(text = "Done")
                   Icon(
                       Icons.Filled.Done,
                       contentDescription = "DoneButton"
                   )
               }
           }
    }
}
   }

}
