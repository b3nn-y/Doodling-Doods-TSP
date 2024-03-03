package com.game.doodlingdoods.screens

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
import com.game.doodlingdoods.R
import com.game.doodlingdoods.screens.utils.CustomOutlinedTextFields
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
            Text(
                text = "Play as Guest Doodler",
                fontSize = 40.sp,
                color = Color.White,
                fontFamily = dayLight,
                modifier = Modifier.padding(20.dp)
            )

            CustomOutlinedTextFields(
                text = guestName,
                onValueChange = { guestName = it },


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
                    .height(80.dp)
                    .clickable(interactionSource = interactionSource,
                        indication = null){
                        playerDetailsViewModel.playerName = guestName
                        navController.navigate("RoomsEntry")
                    }
            )

        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    GuestAccountScreen(navController = NavController(LocalContext.current), playerDetailsViewModel = PlayerDetailsViewModel())

}