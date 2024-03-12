package com.game.doodlingdoods.screens

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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.game.doodlingdoods.R

import com.game.doodlingdoods.screens.utils.CustomPasswordField
import com.game.doodlingdoods.screens.utils.CustomTextField
import com.game.doodlingdoods.ui.theme.signInFontFamily
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel

//This screen is shown if the join game is selected, here the user enters the game id and its passcode.

@Composable
fun JoinRoomScreen(navController: NavController, playerDetailsViewModel: PlayerDetailsViewModel) {
    playerDetailsViewModel.roomAvailability.value = ""
    JoinRoom(navController, playerDetailsViewModel = playerDetailsViewModel)
}

@Composable
private fun JoinRoom(
    navController: NavController,
    modifier: Modifier = Modifier,
    playerDetailsViewModel: PlayerDetailsViewModel
) {
    var roomId by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }

    var checkAvailability by rememberSaveable {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    var roomAvailabilityState by playerDetailsViewModel.roomAvailability
    val currentRoomAvailability = roomAvailabilityState
    val interactionSource = remember { MutableInteractionSource() }
    val lottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login_man_pencil))



    when (currentRoomAvailability) {

        "no room" -> {
            Toast.makeText(
                LocalContext.current,
                "No Room Found with name $roomId",
                Toast.LENGTH_SHORT
            ).show()
            roomAvailabilityState = ""
        }

        "wrong pass" -> {
            Toast.makeText(LocalContext.current, "Incorrect pass for $roomId", Toast.LENGTH_SHORT)
                .show()
            roomAvailabilityState = ""
        }

        "verified" -> {
            Toast.makeText(LocalContext.current, "Verified $roomId", Toast.LENGTH_SHORT).show()
            roomAvailabilityState = ""
            println(playerDetailsViewModel.playerName)
            navController.navigate("LobbyJoinerScreen")
        }
    }

    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "bg image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            LottieAnimation(composition = lottie, iterations = LottieConstants.IterateForever, modifier = Modifier.size(250.dp))

            Text(
                text = "Room name",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = signInFontFamily,
                modifier = Modifier
                    .padding(start = 30.dp, 8.dp)
                    .align(Alignment.Start)
            )

            CustomTextField(
                text = roomId,
                onValueChange = { roomId = it },
                modifier = Modifier
                    .padding(4.dp)
                    .padding(8.dp)
                    .background(Color.Transparent),
                backgroundColor = Color.White,
                placeholder = "Room name"
            )

            Text(
                text = "Password",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = signInFontFamily,
                modifier = Modifier
                    .padding(start = 30.dp, 8.dp)
                    .align(Alignment.Start)
            )

            CustomPasswordField(
                text = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .padding(4.dp)
                    .padding(8.dp)
                    .background(Color.Transparent),
                backgroundColor = Color.White,
                placeholder = "Password"
            )

            Image(painter = painterResource(id = R.drawable.join_room_button),
                contentDescription = "Join Room Button",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        if (roomId.isNotEmpty() && password.isNotEmpty()){
                            playerDetailsViewModel.clickAudio.start()

                            playerDetailsViewModel.roomName = roomId.uppercase().trim()
                            playerDetailsViewModel.roomPass = password.uppercase().trim()

                            playerDetailsViewModel.checkRoomAvailability()
                        }else{
                            Toast.makeText(context,"Check your inputs",Toast.LENGTH_SHORT).show()
                        }

                    }
            )
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun PrevJoinRoom() {
    JoinRoomScreen(NavController(LocalContext.current), PlayerDetailsViewModel)
}