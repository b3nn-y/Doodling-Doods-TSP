package com.game.doodlingdoods.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.game.doodlingdoods.R
import com.game.doodlingdoods.viewmodels.ServerCommunicationViewModel

//This screen is shown after creating a room, where the leader sets the game settings, Here we will show the number of questions, theme, max players etc.
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LobbyAdminScreen(navController: NavHostController) {
    val serverViewModel = hiltViewModel<ServerCommunicationViewModel>()
    val state by serverViewModel.state.collectAsState()
    val isConnecting by serverViewModel.isConnecting.collectAsState()
    val showConnectionError by serverViewModel.showConnectionError.collectAsState()
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar()

            val myList = (1..5).toList()
            LazyColumn {
                items(myList) { element ->
                    PlayerCard()
                }
            }

            Bottombar(navController=navController)
        }

    }

}

@Composable
private fun TopBar(

    modifier: Modifier = Modifier

) {
    Card(
        modifier
            .fillMaxWidth()
            .height(50.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {

        Text(
            text = "Room id",
            fontSize = 20.sp,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
        )

    }
}

@Composable
private fun Bottombar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { navController.navigate("GameScreen")},
        modifier = modifier.padding(16.dp)
    ) {
        Text(text = "Start")
    }
}

@Composable
private fun PlayerCard(
    modifier: Modifier = Modifier
) {

    Card(
        modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_),
                contentDescription = "Profile",
                modifier
                    .size(60.dp)
                    .weight(0.2f)

            )
            Text(
                text = "User Name",
                fontSize = 25.sp,
                modifier = modifier
                    .weight(0.8f)
                    .align(Alignment.CenterVertically)
                    .padding(8.dp)

            )

        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun PreviewGameSettingsScreen() {
//    GameSettingsScreen(navController)
}