package com.reignscanary.jetknow.composables

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.reignscanary.jetknow.*
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
    val contributeLatLng by mainScreenViewModel.contributeLatLng.observeAsState()
   Column(modifier = Modifier
       .fillMaxSize()
       .padding(top = 150.dp, end = 10.dp, start = 10.dp)) {

Box(modifier =  Modifier.weight(0.8f))
{
    Text(
        text = "Add Details here",
        style = TextStyle(color = MaterialTheme.colors.onSurface,fontSize = 30.sp, fontFamily = FontFamily(Font(R.font.opensansbold))),
        modifier = Modifier.padding(start = 20.dp, end = 10.dp),
    )
}
       Box(modifier =  Modifier.weight(3.2f))
       {

       Column() {


           TextField(
               value = name,
           onValueChange ={ name = it },
           modifier = Modifier
               .fillMaxWidth(1f)
               .padding(end = 20.dp, start = 20.dp)
               .clip(MaterialTheme.shapes.large)
               ,
           placeholder = { Text(text = "Enter Name")}
           ,singleLine = true
           )
           TextField(
               value = "$latlng",onValueChange ={},modifier = Modifier
                   .fillMaxWidth(1f)
                   .padding(top = 16.dp, end = 20.dp, start = 20.dp)
                   .clip(MaterialTheme.shapes.large),
               singleLine = true
           )
           Text(
               text ="Category",
               style = TextStyle(color = MaterialTheme.colors.onSurface ,fontSize = 20.sp,fontFamily = FontFamily(Font(R.font.opnsasnsemibold))),
               modifier = Modifier.padding(top=10.dp,start = 20.dp,end = 20.dp),
               textAlign = TextAlign.Center
           )
           CategoriesCarousel(modifier = Modifier
               .requiredSize(100.dp)
               .clip(MaterialTheme.shapes.large)
               .padding(10.dp) )
           SearchText(searchText = searchText,modifier = Modifier
               .padding(8.dp)
                ) {
           mainScreenViewModel.onSearchTextChange(it)
       }
         Box(contentAlignment = Alignment.BottomEnd,modifier = Modifier
             .fillMaxSize(1f)
             .padding(10.dp)) {


             Button(
                 onClick = {
                    updateDetails(context,searchText,name, latlng)

                           },
                 modifier = Modifier.fillMaxWidth(0.30f)) {
                 Text(text = "Done")
                 Icon(Icons.Filled.Done,
                     contentDescription = "DoneButton")

         }}


   }}}

}

fun updateDetails(context: Context, searchText: String, name: String, contributeLatLng: LatLng) {

    val databaseInstance = FirebaseDatabase.getInstance()
    val user = FirebaseAuth.getInstance()
    val  dataRef : DatabaseReference = databaseInstance.reference
    val id = dataRef.push().key.toString()
   val contribution = Contributions(id,contributeLatLng.latitude,contributeLatLng.longitude,name,searchText)


    dataRef.child("Categories").child(searchText).child(id).setValue(contribution).addOnCompleteListener{
         Toast.makeText(context,"Done!!",Toast.LENGTH_SHORT).show()
                context.startActivity(Intent(context,MainActivity::class.java))}
    user.uid?.let { dataRef.child("Contributors").child(it).setValue(contribution.id)
        .addOnSuccessListener {

            Toast.makeText(context,"Thanks for contributing!!",Toast.LENGTH_SHORT).show()
        }


    }


}
