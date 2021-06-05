package com.reignscanary.jetknow.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginPage() {

    var loginemail = remember {
        mutableStateOf("")
    }
    var loginpassword = remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            //.padding(start = 20.dp)
            //.background(myTheme.colors.)
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                //.background(Color.Transparent)
        ) {
            Box(

                modifier = Modifier
                    .weight(1f)
                    //.background(Color.Transparent)
                    .fillMaxSize(),
                //.padding(vertical = 20.dp),

                contentAlignment = Alignment.Center

            ) {
                /*Image(
                    painter = painterResource(id = R.drawable. ),
                    contentDescription = null,
                    modifier = Modifier
                        .requiredSize(80.dp)
                        .fillMaxSize()
                )*/
            }
            Box(modifier = Modifier
                .weight(3f)
                //.background(Color.White)
                //.shadow(20.dp)
                .clip(RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp))
                .background(Color.LightGray)
                .padding(20.dp),
                contentAlignment = Alignment.TopCenter
            )
            {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "LOGIN",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold
                        ),
                        fontSize = 30.sp,
                        color = Color.Blue
                    )
                    Spacer(modifier = Modifier.padding(10.dp))

                    TextField(
                        value = loginemail.value,
                        onValueChange = { loginemail.value = it },
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            //.background(BlueGray100)
                            .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
                            .padding(end = 20.dp, start = 20.dp),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    TextField(
                        value = loginpassword.value,
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            //.background(BlueGray100)
                            .clip(RoundedCornerShape(20.dp))
                            //.clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
                            .padding(end = 20.dp, start = 20.dp),
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier
                            .width(10.dp)
                    ) {
                    }

                }
            }
        }

    }

}