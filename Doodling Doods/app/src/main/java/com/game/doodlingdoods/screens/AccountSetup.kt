package com.game.doodlingdoods.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.game.doodlingdoods.R
import com.game.doodlingdoods.ui.theme.DarkBlue
import com.game.doodlingdoods.ui.theme.dayLight
import com.game.doodlingdoods.ui.theme.ov_soge_bold
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel

//This screen shows the option to either create a temp username and join a game, or to sign up or login with a email account to store their data

@Composable
fun AccountSetup(
    navController: NavController,
    playerDetailsViewModel: PlayerDetailsViewModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "bg image",
            contentScale = ContentScale.FillBounds,
            modifier = modifier.fillMaxSize()
        )

        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                GuestScreen(
                    navController = navController,
                    playerDetailsViewModel = playerDetailsViewModel
                )
            }
        }
    }
}


//for creating guest account
@Composable
private fun GuestScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    playerDetailsViewModel: PlayerDetailsViewModel
) {

    val guestName by rememberSaveable {
        mutableStateOf("")
    }
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier.fillMaxWidth()

    ) {
        Column(
            modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {

            val doodle by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.painting_animation))

            LottieAnimation(
                composition = doodle,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.height(350.dp)
            )

            Text(
                text = "Welcome\nto",
                fontSize = 30.sp,
                fontFamily = dayLight,
                color = DarkBlue,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Doodling Doods",
                fontSize = 40.sp,
                fontFamily = dayLight,
                color = Color.White,
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "To have a flawless experience",
                fontSize = 20.sp,
                fontFamily = ov_soge_bold,
                color = DarkBlue,
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center
            )


            Image(painter = painterResource(id = R.drawable.login_button),
                contentDescription = "Login Button", modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        navController.navigate("LoginScreen")
                    }
                    .height(70.dp))

            Image(painter = painterResource(id = R.drawable.sign_up_button),
                contentDescription = "Sign Up", modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        navController.navigate("SignUpScreen")
                    }
                    .height(70.dp))

            Text(
                text = "or",
                fontSize = 20.sp,
                fontFamily = ov_soge_bold,
                color = DarkBlue,
                textAlign = TextAlign.Center
            )

            Image(painter = painterResource(id = R.drawable.guest_play),
                contentDescription = "Guest Play",
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        playerDetailsViewModel.playerName = guestName
                        navController.navigate("GuestAccountScreen")
                    }
                    .height(70.dp)
            )

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Prev() {
    GuestScreen(
        navController = NavController(LocalContext.current),
        playerDetailsViewModel = PlayerDetailsViewModel()
    )
    AccountSetup(navController = NavController(LocalContext.current), PlayerDetailsViewModel())
}



