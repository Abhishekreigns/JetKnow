package com.reignscanary.jetknow.composables

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.android.gms.maps.model.Marker
import com.google.firebase.database.*
import com.reignscanary.jetknow.backend.MainScreenViewModel
@Composable
fun InfoPopup(mainScreenViewModel: MainScreenViewModel, selectedCategory : String, selectedMarker: Marker){



    val address = remember { mutableStateOf("no:2," +
            " new street," +
            " new area," +
            " chennai-28")
    }
    val context = LocalContext.current
    val databaseInstance = FirebaseDatabase.getInstance()
    val  dataRef : DatabaseReference = databaseInstance.reference.child("Categories").child(selectedCategory).child(selectedMarker.snippet)
var name by remember {
    mutableStateOf("")
}
    var number by remember {
        mutableStateOf("")
    }
    val valueEventListener = object : ValueEventListener {

        override fun onDataChange(snapshot: DataSnapshot) {
            name = snapshot.child("name").value.toString()
            number = snapshot.child("number").value.toString()

        }



        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context,error.message, Toast.LENGTH_SHORT).show()
        }
    }
    dataRef.addValueEventListener(valueEventListener)



    Dialog(onDismissRequest = { mainScreenViewModel.onInfoDialogStatusChanged(false) }) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colors.surface)
                .padding(20.dp)

        ) {
            Column {

                Text(text = "Name :", fontWeight = FontWeight.Bold)
                Text(text = name, color = MaterialTheme.colors.onSurface)
                Spacer(modifier = Modifier.padding(10.dp))
                Text(text = "Address :", fontWeight = FontWeight.Bold)
                Text(text = address.value, color = MaterialTheme.colors.onSurface)
                Spacer(modifier = Modifier.padding(10.dp))
                Text(text = "Mobile number :", fontWeight = FontWeight.Bold)
                Text(text = number, color = MaterialTheme.colors.onSurface)

                Spacer(modifier = Modifier.height(30.dp))

                Row (
                    horizontalArrangement = Arrangement.Center
                ){
                    Spacer(
                        modifier = Modifier.weight(3f)
                    )
                    Button(
                        onClick = {

                            mainScreenViewModel.onInfoDialogStatusChanged(false)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .clip(MaterialTheme.shapes.large)
                    ) {
                        Text(text = "ok")
                    }

                }

            }

        }}
    }

