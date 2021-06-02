package com.reignscanary.jetknow.composables

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.reignscanary.jetknow.ContributeActivity
import com.reignscanary.jetknow.MainScreenViewModel
import com.reignscanary.jetknow.R

@Composable
fun AlertDialogComponent(context: Context, mainScreenViewModel: MainScreenViewModel, latLng: LatLng) {


    val isOpen by mainScreenViewModel.dialogStatus.observeAsState(initial =true )
  //  val wantToContribute by mainScreenViewModel.wantToContribute.observeAsState(initial = false)
    var i : Intent

    if(isOpen){

        AlertDialog(

            onDismissRequest = {mainScreenViewModel.onDialogStatusChanged(false)},
            title = { Text(text = "Want To contribute") },
            text = { Text(text = "Provide Service for your known locations") },
            confirmButton = {

                TextButton(onClick = {
                    // context.startActivity(Intent(context,ContributeActivity::class.java))
                    mainScreenViewModel.onDialogStatusChanged(false)
                    i = Intent(context, ContributeActivity::class.java)
                    i.putExtra("latlng", latLng)
                    context.startActivity(i)
                    Toast.makeText(context,"Another Screen Will Open,you can add the details there",
                        Toast.LENGTH_SHORT).show()



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
            backgroundColor = colorResource(id = R.color.cardview_light_background),
            contentColor = Color.Black,
            modifier = Modifier.clip(RoundedCornerShape(10.dp))




        )



    }





}
