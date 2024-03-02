package com.game.doodlingdoods.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.playerManager.Player
import com.example.roomManager.Room
import com.game.doodlingdoods.screens.utils.UserCard
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel
import com.game.doodlingdoods.viewmodels.ServerCommunicationViewModel
import com.google.gson.Gson


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LobbyJoinerScreen(navController: NavController, playerDetailsViewModel: PlayerDetailsViewModel) {
    val serverViewModel = hiltViewModel<ServerCommunicationViewModel>()
    val state by serverViewModel.state.collectAsState()



//    val isConnecting by serverViewModel.isConnecting.collectAsState()
//    val showConnectionError by serverViewModel.showConnectionError.collectAsState()
    Log.i("State",state)

    val isConnected by serverViewModel.isConnectedWithServer.collectAsState()

    Log.i("Recompose",isConnected.toString())

    if (!isConnected)  RoomsHandler(serverViewModel,state,playerDetailsViewModel)

    var roomChanges = Room("", "", ArrayList<Player>(), 0, 0,Player("", "", "", ""), false, 0, "" , false, Player("", "", "", ""), 3, "")

    var roomUpdates by remember {
        mutableStateOf(roomChanges)
    }

    try {
        var tempData = serverViewModel.evaluateServerMessage(state)
        if (tempData != null){
            roomChanges = tempData
        }
    }
    catch (e:Exception){
        Log.i("LobbyJoiner",e.toString())
    }
    println("Rooom Updated $roomUpdates")




    if (serverViewModel.isGameStarted){
        navController.navigate("GameScreen")
    }

    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar()
            val myList = serverViewModel.playersList

            LazyColumn {
                items(myList) { playerName ->
                    PlayerCard(playerName = playerName)
                }
            }


        }

    }

}

@Composable
private fun RoomsHandler(
    serverViewModel: ServerCommunicationViewModel,
    state: String,
    playerDetailsViewModel: PlayerDetailsViewModel
) {

//    if (!isConnected)
    playerDetailsViewModel.initializeServerViewModel(serverViewModel)
    if (state.startsWith("You have been successfully")) {
        println("This is called\n\n\n${serverViewModel.sendMessage(Gson().toJson(playerDetailsViewModel.getPlayerData()))}")


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
private fun PlayerCard(
    modifier: Modifier = Modifier,
    playerName: String
) {

    UserCard( playerName)

}

@Preview(showSystemUi = true)
@Composable
fun PreviewJoinerLobby() {
    LobbyJoinerScreen(
        navController = NavController(LocalContext.current),
        PlayerDetailsViewModel()
    )
}