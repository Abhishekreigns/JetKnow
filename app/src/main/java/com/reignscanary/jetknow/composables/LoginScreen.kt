package com.reignscanary.jetknow.composables

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.LatLng
import com.reignscanary.jetknow.activities.ContributeActivity
import com.reignscanary.jetknow.R

@Composable
fun LoginPage(latlng: LatLng?) {
    var i : Intent
    val context = LocalContext.current
    val loginemail = remember {
        mutableStateOf("")
    }
    val topRoundedCards = RoundedCornerShape(topStart = 16.dp,topEnd = 16.dp)
    val loginpassword = remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier

        ) {
            Box(

                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),

                contentAlignment = Alignment.Center

            ) {

            }
            Box(modifier = Modifier
                .weight(3f)
                .clip(topRoundedCards)
                .background(MaterialTheme.colors.surface)
                .padding(20.dp),
                contentAlignment = Alignment.TopCenter
            )
            {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Login Here",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.opnsansregular))
                        ),
                        fontSize = 30.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                    Spacer(modifier = Modifier.padding(10.dp))

                    TextField(
                        value = loginemail.value,
                        onValueChange = { loginemail.value = it },
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(end = 20.dp, start = 20.dp)
                            .clip(MaterialTheme.shapes.large),
                        singleLine = true,
                        placeholder = {
                            Text(text = "Enter Mail")
                        }
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    TextField(
                        value = loginpassword.value,
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(end = 20.dp, start = 20.dp)
                            .clip(MaterialTheme.shapes.large),
                        singleLine = true,
                        placeholder = {
                            Text(text = "Enter Password")
                        }
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(onClick = {

                        i = Intent(context, ContributeActivity::class.java)

                        context.startActivity(i)
                    },
                        modifier = Modifier

                    ) {
                        Text(text = "LOGIN",style = TextStyle( fontFamily = FontFamily(Font(
                            R.font.opensansbold))))

                    }
                    Text(
                        text = "REGISTER",
                        style = TextStyle(fontFamily = FontFamily(Font(
                        R.font.opensansbold))),
                        modifier = Modifier.clickable{
                            /*TODO*/
                        }
                    )
                     IconButton(onClick = {
                         /*TODO*/

                     },

                         modifier = Modifier.padding(top = 10.dp)
                ){
                         Icon(painter = painterResource(id = R.drawable.google),"Google")
                     }


                }
            }
        }

    }

}