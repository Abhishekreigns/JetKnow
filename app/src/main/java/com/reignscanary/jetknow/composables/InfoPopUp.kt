package com.reignscanary.jetknow.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Preview(showBackground = true)
@Composable
fun InfoPopup(){

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
                .background(MaterialTheme.colors.surface)
                .padding(20.dp)

        ) {
            Column {

                Text(text = "name :", fontWeight = FontWeight.Bold)
                Text(text = name.value, color = MaterialTheme.colors.onSurface)
                Text(text = "address :", fontWeight = FontWeight.Bold)
                Text(text = address.value, color = MaterialTheme.colors.onSurface)
                Text(text = "mobile number :", fontWeight = FontWeight.Bold)
                Text(text = "${mobileNumber.value}", color = MaterialTheme.colors.onSurface)

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
                            .clip(MaterialTheme.shapes.large)
                    ) {
                        Text(text = "ok")
                    }

                }

            }

        }
    }

}
