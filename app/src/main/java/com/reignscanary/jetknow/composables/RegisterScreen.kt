package com.reignscanary.jetknow.composables

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun RegisterPage() {

    val registeremail = remember {
        mutableStateOf("")
    }
    val topRoundedCards = RoundedCornerShape(topStart = 16.dp,topEnd = 16.dp)
    var registerpassword = remember {
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
                        text = "Register Here",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold
                        ),
                        fontSize = 30.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                    Spacer(modifier = Modifier.padding(10.dp))

                    TextField(
                        value = registeremail.value,
                        onValueChange = { registeremail.value = it },
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
                        value = registerpassword.value,
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
                    TextField(
                        value = registerpassword.value,
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(end = 20.dp, start = 20.dp)
                            .clip(MaterialTheme.shapes.large),
                        singleLine = true,
                        placeholder = {
                            Text(text = "Re-enter Password")
                        }
                    )

                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(onClick = {

                    },
                        modifier = Modifier

                    ) {
                        Text(text = "REGISTER")

                    }

                }
            }
        }

    }

}
