package com.game.doodlingdoods.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.game.doodlingdoods.screens.utils.CustomTextField

import com.game.doodlingdoods.ui.theme.dayLight
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel

@Composable
fun GuestAccountScreen(
    navController: NavController,
    playerDetailsViewModel: PlayerDetailsViewModel
) {
    GuestScreen(navController = navController, playerDetailsViewModel = playerDetailsViewModel)
}
//for creating guest account
@Composable
private fun GuestScreen(modifier: Modifier = Modifier, navController: NavController, playerDetailsViewModel: PlayerDetailsViewModel) {
    var guestName by rememberSaveable {
        mutableStateOf("")
    }

    val interactionSource = remember{
        MutableInteractionSource()
    }
    val context = LocalContext.current

    val lottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.sign_up_pencil))

    Image(
        painter = painterResource(id = R.drawable.background),
        contentDescription = "bg image",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )

    Box(
        modifier
            .fillMaxWidth(),

        ) {
        Column(
            modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LottieAnimation(composition = lottie, iterations = LottieConstants.IterateForever, modifier = Modifier.size(250.dp))

            Text(
                text = "Play as Guest",
                fontSize = 40.sp,
                color = Color.White,
                fontFamily = dayLight,
                modifier = Modifier.padding(20.dp)
            )

            CustomTextField(
                text = guestName,
                onValueChange = { guestName = it },
                placeholder = "Name"

//                value = guestName,
//                onValueChange = { guestName = it },
//                modifier
//                    .padding(horizontal = 35.dp)
//                    .padding(16.dp)
//                    .fillMaxWidth(),
//                label = { Text(text = "Name") },
//                singleLine = true

            )

            Spacer(modifier = Modifier.height(20.dp))

            Image(painter = painterResource(id = R.drawable.guest_play), contentDescription = "Guest Play Button",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .clickable(interactionSource = interactionSource,
                        indication = null){
                        val name = guestName.trim()

                        if(name != ""){
                            playerDetailsViewModel.clickAudio.start()

                            playerDetailsViewModel.playerName = guestName.trim().take(8)
                            println("The name in player view Model is ${playerDetailsViewModel.playerName}")

                            navController.navigate("RoomsEntry")
                        }else{
                            Toast.makeText(context,"Please fill your name",Toast.LENGTH_SHORT).show()
                        }

                    }
            )

        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    GuestAccountScreen(navController = NavController(LocalContext.current), playerDetailsViewModel = PlayerDetailsViewModel)

}