package com.reignscanary.jetknow.composables

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.reignscanary.jetknow.activities.ContributeActivity
import com.reignscanary.jetknow.activities.LoginActivity
import com.reignscanary.jetknow.backend.MainScreenViewModel

@Composable
fun AlertDialogComponent(context: Context, mainScreenViewModel: MainScreenViewModel, latLng: LatLng) {


    val isOpen by mainScreenViewModel.dialogStatus.observeAsState(initial =true )
    var i : Intent

    if(isOpen){

        AlertDialog(

            onDismissRequest = {mainScreenViewModel.onDialogStatusChanged(false)},
            title = { Text(text = "Want To contribute") },
            text = { Text(text = "Provide Service for your known locations") },
            confirmButton = {

                TextButton(onClick = {
                    mainScreenViewModel.onDialogStatusChanged(false)
                   // i = Intent(context, ContributeActivity::class.java)
                    val user = FirebaseAuth.getInstance().currentUser
                    if (user != null) {
                        i= Intent(context, ContributeActivity::class.java)
                        i.putExtra("latlng", latLng)
                        context.startActivity(i)

                    }
                    else{
                        val loginActivityIntent = Intent(context, LoginActivity::class.java)
                        context.startActivity(loginActivityIntent)
                    }





                }) {

                    Text(text = "Ok Add")


                }

            },
            dismissButton = {
                TextButton(onClick = {

                    mainScreenViewModel.onDialogStatusChanged(false)



                }) {
                    Text(text = "Clicked by mistake")
                }



            },
            backgroundColor = MaterialTheme.colors.background,
            contentColor =MaterialTheme.colors.onBackground,
            modifier = Modifier.clip(MaterialTheme.shapes.large)
        )



    }





}
