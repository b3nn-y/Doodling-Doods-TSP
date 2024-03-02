package com.game.doodlingdoods.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel

@Composable
fun GuestAccountScreen(
    navController: NavController,
    playerDetailsViewModel: PlayerDetailsViewModel
) {
    GuestButton(navController = navController, playerDetailsViewModel = playerDetailsViewModel)
}
//for creating guest account
@Composable
private fun GuestButton(modifier: Modifier = Modifier, navController: NavController, playerDetailsViewModel: PlayerDetailsViewModel) {
    var guestName by rememberSaveable {
        mutableStateOf("")
    }
    Box(
        modifier.fillMaxWidth()

    ) {
        Column(
            modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Play as Guest User",
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )


            OutlinedTextField(
                value = guestName,
                onValueChange = { guestName = it },
                modifier
                    .padding(horizontal = 35.dp)
                    .padding(16.dp)
                    .fillMaxWidth(),
                label = { Text(text = "Name") },
                singleLine = true


            )


            Card(
                modifier
                    .width(200.dp)
                    .padding(20.dp)

            ) {
                Text(
                    text = "Play",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp)
                        .clickable {
                            playerDetailsViewModel.playerName = guestName
                            navController.navigate("RoomsEntry")
                        }
                )
            }


        }
    }

}