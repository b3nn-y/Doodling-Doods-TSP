package com.game.doodlingdoods.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.game.doodlingdoods.R
import com.game.doodlingdoods.internetConnection.ConnectivityObserver
import com.game.doodlingdoods.internetConnection.NetworkConnectivityObserver
import com.game.doodlingdoods.ui.theme.signInFontFamily
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
    val interactionSource = remember { MutableInteractionSource() }

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

        Image(painter = painterResource(id = R.drawable.background),
            contentDescription ="bg image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds)

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            Text(
                text = "Room id",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = signInFontFamily,
                modifier = Modifier
                    .padding(start = 30.dp, 8.dp)
                    .align(Alignment.Start)
            )

            CustomOutlinedTextField(
                text = roomId,
                onValueChange = {roomId = it},
                modifier = Modifier
                    .padding(4.dp)
                    .padding(8.dp)
                    .background(Color.Transparent),
                backgroundColor = Color.White)

            Text(
                text = "Password",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = signInFontFamily,
                modifier = Modifier
                    .padding(start = 30.dp, 8.dp)
                    .align(Alignment.Start)
            )

            CustomOutlinedPasswordField(
                text = password,
                onValueChange = {password = it},
                modifier = Modifier
                    .padding(4.dp)
                    .padding(8.dp)
                    .background(Color.Transparent),
                backgroundColor = Color.White
            )



            //for hide password

//                visualTransformation =  PasswordVisualTransformation(),
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),


            Image(
                painter = painterResource(id = R.drawable.create_room_button),
                contentDescription = "create room button",
                modifier = Modifier
                    .clickable {

                        if ( userInputFilter(roomId,password)) {
                            playerDetailsViewModel.roomName = roomId
                            playerDetailsViewModel.roomPass = password
                            playerDetailsViewModel.checkRoomAvailability()
                        }else{
                            Log.i("inputfilter","wrong credentials")
                        }
                    }




            )

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

@Composable
private fun CustomOutlinedTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White
) {
    Surface(
        shadowElevation = 20.dp,
        shape = RoundedCornerShape(50),
        modifier = modifier.fillMaxWidth(0.75f),
        color = backgroundColor
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            OutlinedTextField(
                shape = RoundedCornerShape(50),
                value = text,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = TextStyle.Default.copy(fontSize = 20.sp),
                singleLine = true,
            )
        }
    }
}

@Composable
private fun CustomOutlinedPasswordField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White
) {
    Surface(
        shadowElevation = 20.dp,
        shape = RoundedCornerShape(50),
        modifier = modifier.fillMaxWidth(0.75f),
        color = backgroundColor
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            OutlinedTextField(
                shape = RoundedCornerShape(50),
                value = text,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = TextStyle.Default.copy(fontSize = 20.sp),
            )
        }
    }
}