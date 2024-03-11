package com.game.doodlingdoods.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.game.doodlingdoods.R
import com.game.doodlingdoods.screens.utils.UserCard
import com.game.doodlingdoods.ui.theme.ov_soge_bold
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel
import com.game.doodlingdoods.viewmodels.ServerCommunicationViewModel

//This screen is shown as soon as a game is over and shows the top scores of the current players and their stats.
@Composable
fun EndGameScreen(
    navController: NavController,
    playerDetailsViewModel: PlayerDetailsViewModel

) {
    playerDetailsViewModel.serverCommunicationViewModel?.room?.players?.forEach {
        if (it.name == playerDetailsViewModel.playerName){
            it.score = playerDetailsViewModel.serverCommunicationViewModel?.score!!
            playerDetailsViewModel.serverCommunicationViewModel?.sendRoomUpdate()
        }
    }

    GameOver(
        playerViewModel = playerDetailsViewModel,
        navController = navController
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun GameOver(
    modifier: Modifier = Modifier,
    playerViewModel: PlayerDetailsViewModel,
    navController: NavController
) {



    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomBar(
            playerDetailsViewModel = playerViewModel,
            navController = navController
        )}
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
            modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier
                .padding(16.dp)
                .fillMaxSize()
                .wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier

                    .fillMaxHeight(0.7f),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                shape = RoundedCornerShape(30.dp)
            ) {
                while (playerViewModel.serverCommunicationViewModel?.playersList?.size != playerViewModel.serverCommunicationViewModel?.playerScoreHashMap?.size){
                    playerViewModel.serverCommunicationViewModel?.playersList!!.forEach {
                        if (!playerViewModel.serverCommunicationViewModel!!.playerScoreHashMap.containsKey(it.name)){
                            playerViewModel.serverCommunicationViewModel!!.playerScoreHashMap.put(it.name, 0)
                        }
                    }
                }
                playerViewModel.serverCommunicationViewModel?.let { it1 -> Body(serverViewModel = it1,
                    playerScoreDesc = playerViewModel.serverCommunicationViewModel!!.playerScoreHashMap,
                   )
                }
            }
        }
    }

//    playerViewModel.serverCommunicationViewModel?.closeCommunication()


}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Turn Over",
            fontSize = 40.sp,
            fontFamily = ov_soge_bold,
            color = Color.White,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Scores",
            fontSize = 40.sp,
            fontFamily = ov_soge_bold,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun Body(
    modifier: Modifier = Modifier,
    playerScoreDesc: Map<String, Int>, // Add parameter for player scores
    serverViewModel: ServerCommunicationViewModel
) {

    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Log.i("Hashmaplazy",playerScoreDesc.toString())
        items(playerScoreDesc.keys.toMutableList()){
            UserCard(playerName = it, admin = false, score = playerScoreDesc[it]?: 0 )
        }
    }
}


@Composable
private fun BottomBar (
    modifier: Modifier=Modifier,
    playerDetailsViewModel: PlayerDetailsViewModel,
    navController: NavController
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.return_to_lobby),
            contentDescription ="Return to lobby",
            modifier
                .fillMaxWidth(0.5f)
                .clickable {
                    navController.navigate("RoomsEntry")
//                if (playerDetailsViewModel.admin){
//                    playerDetailsViewModel.serverCommunicationViewModel = null
//                    navController.navigate("LobbyAdminScreen")
//                }else{
//                    playerDetailsViewModel.serverCommunicationViewModel = null
//                    navController.navigate("LobbyJoinerScreen")
//                }
                }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevEndGame() {
    EndGameScreen(navController= NavController(LocalContext.current), PlayerDetailsViewModel)
}