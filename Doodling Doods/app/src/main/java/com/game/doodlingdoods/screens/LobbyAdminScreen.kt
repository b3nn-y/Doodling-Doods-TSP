package com.game.doodlingdoods.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.playerManager.Player
import com.example.roomManager.Room
import com.game.doodlingdoods.R
import com.game.doodlingdoods.screens.utils.UserCard
import com.game.doodlingdoods.ui.theme.ov_soge_bold
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel
import com.game.doodlingdoods.viewmodels.ServerCommunicationViewModel
import com.google.gson.Gson

//This screen is shown after creating a room, where the leader sets the game settings, Here we will show the number of questions, theme, max players etc.
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LobbyAdminScreen(
    navController: NavHostController,
    playerDetailsViewModel: PlayerDetailsViewModel
) {
    val serverViewModel = hiltViewModel<ServerCommunicationViewModel>()
    val state by serverViewModel.state.collectAsState()
    val isConnecting by serverViewModel.isConnecting.collectAsState()
    val showConnectionError by serverViewModel.showConnectionError.collectAsState()


    playerDetailsViewModel.initializeServerViewModel(serverViewModel)
    serverViewModel.sendMessage(Gson().toJson(playerDetailsViewModel.getPlayerData()))

    serverViewModel.evaluateServerMessage(state)

    var roomChanges = Room(
        "", "", ArrayList<Player>(), 0, 0,
        Player("", "", "", ""), false, 0, "", false, Player("", "", "", ""), 3, "", wordList = arrayListOf(), guessedPlayers = arrayListOf(), iosCords = arrayListOf(),messages = arrayListOf()
    )



    var roomUpdates by remember {
        mutableStateOf(roomChanges)
    }

    try {
        var tempData = serverViewModel.evaluateServerMessage(state)
        if (tempData != null) {
            roomChanges = tempData
        }
    } catch (e: Exception) {

    }
    println("Room Updated $roomUpdates")

    Scaffold(
        topBar = {TopBar(playerDetailsViewModel = playerDetailsViewModel)},
        bottomBar = {BottomBar(navController = navController, serverViewModel,playerDetailsViewModel)}
    ) {

        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "bg image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            val myList = serverViewModel.playersList

            Card(
                modifier = Modifier

                    .padding(8.dp)
                    .padding(8.dp)
                    .fillMaxHeight(0.6f)
                    .align(Alignment.Start),
                colors = CardDefaults.cardColors(Color.White),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 20.dp
                )
            ) {
                Text(
                    text = "Players joined ${serverViewModel.playersList.size}/10",
                    fontSize = 14.sp,
                    fontFamily = ov_soge_bold,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                    color = Color.Black
                )
                LazyColumn {
                    items(myList) { player->
                        UserCard(playerName = player.name, admin = player.admin, profile = player.profile)
                    }
                }
            }


        }

    }

}

@Composable
private fun TopBar(

    modifier: Modifier = Modifier,
    playerDetailsViewModel: PlayerDetailsViewModel

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

//        Text(
//            text = "${playerDetailsViewModel.playerName}'s Room",
//            fontFamily = ov_soge_bold,
//            fontSize = 20.sp,
//            color = Color.Black,
//            modifier = modifier
//                .align(Alignment.CenterHorizontally)
//                .padding(10.dp)
//        )

        Text(
            text = "Room name :${playerDetailsViewModel.roomName}",
            fontFamily = ov_soge_bold,
            fontSize = 16.sp,
            color = Color.Blue,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )

        Text(
            text = "password : ${playerDetailsViewModel.roomPass}",
            fontFamily = ov_soge_bold,
            fontSize = 16.sp,
            color = Color.Blue,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)

        )

//        Row(
//            modifier = Modifier
//                .padding(8.dp)
//                .align(Alignment.CenterHorizontally)
//        ) {
//
//            Image(
//                painter = painterResource(id = R.drawable.copy),
//                contentDescription = "copy image",
//                modifier = Modifier
//                    .height(40.dp)
//                    .clickable(
//                        interactionSource = interactionSource,
//                        indication = null
//                    ) {
//                        //Copy
//                    }
//            )
//
//            Image(
//                painter = painterResource(id = R.drawable.share),
//                contentDescription = "share image",
//                modifier = Modifier
//                    .height(40.dp)
//                    .padding(start = 40.dp)
//                    .clickable(
//                        interactionSource = interactionSource,
//                        indication = null
//                    ) {
//                        //Share
//                    }
//            )
//        }

    }
}


@Composable
private fun BottomBar(
    navController: NavController,
    serverCommunicationViewModel: ServerCommunicationViewModel,
    playerDetailsViewModel: PlayerDetailsViewModel,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.startgame),
            contentDescription = "Game Settings",
            modifier = Modifier
                .padding(8.dp, bottom = 20.dp)
                .fillMaxWidth(0.5f)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    if (serverCommunicationViewModel.playersList.size>1){

                        serverCommunicationViewModel.room.gameStarted = true
                        serverCommunicationViewModel.sendRoomUpdate()
                        navController.navigate("GameScreen")
                    }else{
                        Toast.makeText(context,"Need at least 2 players",Toast.LENGTH_SHORT).show()
                    }

                }
        )
    }

//    Button(
//        onClick = {
//            serverCommunicationViewModel.room.gameStarted = true
//            serverCommunicationViewModel.sendRoomUpdate()
//            navController.navigate("GameScreen")
//        },
//        modifier = modifier.padding(16.dp)
//    ) {
//        Text(text = "Start")
//    }
}



//@Preview(showSystemUi = true)
//@Composable
//fun PreviewGameSettingsScreen() {
//    LobbyJoinerScreen(navController = NavController(LocalContext.current), playerDetailsViewModel = PlayerDetailsViewModel)
////    BottomBar(navController = NavController(LocalContext.current), serverCommunicationViewModel = ServerCommunicationViewModel())
////    GameSettingsScreen(navController)
//}