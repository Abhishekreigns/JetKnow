package com.reignscanary.jetknow.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun infoPopup(){

    var name = remember { mutableStateOf("contributor1") }
    var address = remember { mutableStateOf("no:2," +
            " new street," +
            " new area," +
            " chennai-28")
    }
    var mobileNumber = remember { mutableStateOf(1234567890) }

    Dialog(onDismissRequest = { /*TODO*/ }) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.LightGray)
                .padding(20.dp)

        ) {
            Column {


                /*
                Image(

                    painter = painterResource(R.drawable.ic),
                    contentDescription = "image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )
                */

                Text(text = "name :", fontWeight = FontWeight.Bold)
                Text(text = "${name.value}", color = Color.White)
                Text(text = "address :", fontWeight = FontWeight.Bold)
                Text(text = "${address.value}", color = Color.White)
                Text(text = "mobile number :", fontWeight = FontWeight.Bold)
                Text(text = "${mobileNumber.value}", color = Color.White)

                Spacer(modifier = Modifier.height(30.dp))

                Row (
                    horizontalArrangement = Arrangement.Center
                ){
                    Spacer(
                        modifier = Modifier.weight(3f)
                    )
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(20.dp))
                    ) {
                        Text(text = "ok")
                    }

                }

            }

        }
    }

}
