package com.reignscanary.jetknow.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reignscanary.jetknow.R

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
                .clip(MaterialTheme.shapes.large)
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
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier

                    ) {
                        Text(text = "LOGIN",style = TextStyle( fontFamily = FontFamily(Font(
                            R.font.opensansbold))))

                    }

                }
            }
        }

    }

}