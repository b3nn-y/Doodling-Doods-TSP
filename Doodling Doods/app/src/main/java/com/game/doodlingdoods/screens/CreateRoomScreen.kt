package com.game.doodlingdoods.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.game.doodlingdoods.internetConnection.ConnectivityObserver
import com.game.doodlingdoods.internetConnection.NetworkConnectivityObserver
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel

//THis is the screen where the user creates the room, with a passcode. (THis should also have the room related settings.)
@Composable
fun CreateRoomScreen(
    navController: NavController,
    playerDetailsViewModel: PlayerDetailsViewModel
) {
    playerDetailsViewModel.roomAvailability.value = ""
    val connectivityObserver: ConnectivityObserver =
        NetworkConnectivityObserver(LocalContext.current)
    CreateRoom(navController,
        playerDetailsViewModel,
        connectivityObserver=connectivityObserver
    )
}

@Composable
private fun CreateRoom(
    navController: NavController,
    playerDetailsViewModel: PlayerDetailsViewModel,
    modifier: Modifier = Modifier,
    connectivityObserver: ConnectivityObserver

    ) {
    var roomId by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }

    val networkStatus by connectivityObserver.observe().collectAsState(
        initial = ConnectivityObserver.Status.Unavailable
    )

    var roomAvailabilityState by playerDetailsViewModel.roomAvailability
    val currentRoomAvailability = roomAvailabilityState

    when (currentRoomAvailability) {
        "" -> {}
        "no room" -> {
            roomAvailabilityState = ""
            playerDetailsViewModel.admin = true
            navController.navigate("LobbyAdminScreen")
        }

        "wrong pass" -> {
            Toast.makeText(
                LocalContext.current,
                "Room $roomId already exists, try another name",
                Toast.LENGTH_SHORT
            ).show()
            roomAvailabilityState = ""
        }

        "verified" -> {
            Toast.makeText(
                LocalContext.current,
                "Room $roomId already exists, try another name",
                Toast.LENGTH_SHORT
            ).show()
            roomAvailabilityState = ""
        }
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
                    if (networkStatus.toString()=="Available"){
                        if ( userInputFilter(roomId,password)) {
                            playerDetailsViewModel.roomName = roomId
                            playerDetailsViewModel.roomPass = password
                            playerDetailsViewModel.checkRoomAvailability()
                        }
                    }

                }
            ) {
                Text(text = "Create")
            }

            if (networkStatus.toString() =="Unavailable" || networkStatus.toString() =="Lost"){
                CircularProgressIndicator(modifier = Modifier.width(50.dp))
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewCreateRoom() {
    CreateRoomScreen(navController = NavController(LocalContext.current),PlayerDetailsViewModel())
}

private fun userInputFilter(room_id: String, password: String): Boolean {
    val regex= Regex("^[a-zA-Z][a-zA-Z0-9_-]{2,19}\$")

    return room_id.matches(regex = regex) && password.matches(regex)


}