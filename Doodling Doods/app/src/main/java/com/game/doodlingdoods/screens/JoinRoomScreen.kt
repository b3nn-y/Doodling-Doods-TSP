package com.game.doodlingdoods.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

//This screen is shown if the join game is selected, here the user enters the game id and its passcode.

@Composable
fun JoinRoomScreen(navController: NavHostController) {
    JoinRoom(navController)
}

@Composable
private fun JoinRoom(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var roomId by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            OutlinedTextField(
                value = roomId,
                onValueChange = { roomId = it },
                label = {
                    Text(
                        text = "Room Id",
                        fontSize = 20.sp
                    )
                },
                modifier = modifier
                    .padding(4.dp)
                    .padding(
                        8.dp
                    )
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = {
                    Text(
                        text = "Password",
                        fontSize = 20.sp
                    )
                },
                modifier = modifier
                    .padding(4.dp)
                    .padding(
                        8.dp
                    ),

                //for hide password

//                visualTransformation =  PasswordVisualTransformation(),
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            )

            Button(
                onClick = {
                    if (roomId.isNotEmpty() && password.isNotEmpty()) {
                        navController.navigate("LobbyJoinerScreen")
                    }
                }
            ) {
                Text(text = "Join")
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun PrevJoinRoom() {
//    JoinRoomScreen(navController)
}