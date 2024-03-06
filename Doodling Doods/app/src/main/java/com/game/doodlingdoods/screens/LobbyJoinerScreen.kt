package com.game.doodlingdoods.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.playerManager.Player
import com.example.roomManager.Room
import com.game.doodlingdoods.R
import com.game.doodlingdoods.screens.utils.UserCard
import com.game.doodlingdoods.ui.theme.ov_soge_bold
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel
import com.game.doodlingdoods.viewmodels.ServerCommunicationViewModel
import com.google.gson.Gson

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LobbyJoinerScreen(
    navController: NavController,
    playerDetailsViewModel: PlayerDetailsViewModel
) {
    val serverViewModel = hiltViewModel<ServerCommunicationViewModel>()
    val state by serverViewModel.state.collectAsState()


//    val isConnecting by serverViewModel.isConnecting.collectAsState()
//    val showConnectionError by serverViewModel.showConnectionError.collectAsState()
    Log.i("State", state)

    val isConnected by serverViewModel.isConnectedWithServer.collectAsState()

    Log.i("Recompose", isConnected.toString())

    if (!isConnected) RoomsHandler(serverViewModel, state, playerDetailsViewModel)

    var roomChanges = Room(
        "",
        "",
        ArrayList<Player>(),
        0,
        0,
        Player("", "", "", ""),
        false,
        0,
        "",
        false,
        Player("", "", "", ""),
        3,
        "",
        wordList = arrayListOf(), guessedPlayers = arrayListOf()
    )

    val roomUpdates by remember {
        mutableStateOf(roomChanges)
    }

    try {
        var tempData = serverViewModel.evaluateServerMessage(state)
        if (tempData != null) {
            roomChanges = tempData
        }
    } catch (e: Exception) {
        Log.i("LobbyJoiner", e.toString())
    }
    println("Room Updated $roomUpdates")




    if (serverViewModel.isGameStarted) {
        navController.navigate("GameScreen")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "bg image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment =
            Alignment.CenterHorizontally
        ) {

            TopBar()

            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .padding(8.dp)
                    .align(Alignment.Start),
                colors = CardDefaults.cardColors(Color.White),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 20.dp
                )
            )
            {
                Text(
                    text = "Players 2/10",
                    fontSize = 14.sp,
                    fontFamily = ov_soge_bold,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                    color = Color.Black
                )
                val myList = serverViewModel.playersList

                LazyColumn{
                    items(myList) { playerName ->
                        PlayerCard(playerName = playerName)
                    }
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
        println(
            "This is called\n\n\n${
                serverViewModel.sendMessage(
                    Gson().toJson(
                        playerDetailsViewModel.getPlayerData()
                    )
                )
            }"
        )
    }

}

@Composable
private fun TopBar(

    modifier: Modifier = Modifier

) {
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier
            .fillMaxWidth()
            .padding(8.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        shape = RoundedCornerShape(20),
        colors = CardDefaults.cardColors(
            Color.White
        )
    ) {

        Text(
            text = "testBot's Room",
            fontFamily = ov_soge_bold,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
        )

        Text(
            text = "Room Id : TestRoom1",
            fontFamily = ov_soge_bold,
            fontSize = 16.sp,
            color = Color.Blue,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(4.dp)
        )

        Text(
            text = "password : ben",
            fontFamily = ov_soge_bold,
            fontSize = 16.sp,
            color = Color.Blue,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(4.dp)
        )

        Row(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        ) {

            Image(
                painter = painterResource(id = R.drawable.copy),
                contentDescription = "copy image",
                modifier = Modifier
                    .height(40.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        //Copy
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.share),
                contentDescription = "share image",
                modifier = Modifier
                    .height(40.dp)
                    .padding(start = 40.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        //Share
                    }
            )
        }

    }
}


@Composable
private fun PlayerCard(
    modifier: Modifier = Modifier,
    playerName: String
) {
    UserCard(playerName)
}
//
//@Preview(showSystemUi = true)
//@Composable
//fun PreviewJoinerLobby() {
//
////    TopBar()
//    LobbyJoinerScreen(
//        navController = NavController(LocalContext.current),
//        PlayerDetailsViewModel()
//    )
//}