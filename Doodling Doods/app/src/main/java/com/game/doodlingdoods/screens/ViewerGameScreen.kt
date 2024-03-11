package com.game.doodlingdoods.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.game.doodlingdoods.R
import com.game.doodlingdoods.screens.utils.ChatBar
import com.game.doodlingdoods.ui.theme.GameBlue
import com.game.doodlingdoods.ui.theme.ov_soge_bold
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel
import com.game.doodlingdoods.viewmodels.ServerCommunicationViewModel
import java.time.format.TextStyle
import androidx.compose.ui.text.*
import com.game.doodlingdoods.screens.utils.ViewersPopUp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

// this is the ongoing game screen, where the live drawing is shown, along with hints, chat, players and their scores, timer etc.
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun ViewerGameScreen(
    navController: NavController,
    playerDetailsViewModel: PlayerDetailsViewModel
) {
    val serverViewModel = playerDetailsViewModel.serverCommunicationViewModel

    val state by serverViewModel!!.state.collectAsState()

    val roomTime by serverViewModel!!.roomTime.collectAsState()


    val currentPlayer by serverViewModel!!.currentPlayer.collectAsState()
    serverViewModel!!.evaluateServerMessage(state)

    val roundsPlayed by serverViewModel.roundsPlayed.collectAsState()
    Log.i("Rounds", roundsPlayed.toString())

    var isPopedUp by rememberSaveable {
        mutableStateOf(true)
    }
    var isWordChosen by serverViewModel.isWordChosen

    if (serverViewModel.room.gameOver) {
        navController.navigate("LeaderBoardScreen")
    }
    serverViewModel.userChosenWord = null

    if (currentPlayer == playerDetailsViewModel.playerName) {

        navController.navigate("DrawingScreen")
    }

    val increasingNumber by serverViewModel.increasingNumber.collectAsState()
    Scaffold(
        //re used our existing chat bar in drawing screen

        bottomBar = {
            if (isWordChosen) {
                UpdateChat(incNum = increasingNumber, serverViewModel = serverViewModel, playerDetailsViewModel = playerDetailsViewModel)
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(R.drawable.background), contentScale = ContentScale.FillBounds
                )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = serverViewModel.room.gameMode,
                        fontFamily = ov_soge_bold,
                        fontSize = 30.sp,
                        modifier = Modifier
                            .weight(0.8f),

                        textAlign = TextAlign.Center,
                        color = Color.White

                    )
                    Image(
                        painter = painterResource(id = R.drawable.people),
                        contentDescription = "profile",
                        modifier = Modifier
                            .size(100.dp)

                            .weight(0.2f),
                        alignment = Alignment.Center,

                        )
                }
                Text(
                    text = "$currentPlayer's turn",
                    modifier = Modifier.padding(8.dp),
                    fontFamily = ov_soge_bold,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    color = GameBlue

                )
                Text(
                    text = roomTime,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 20.sp
                    )

                )

                Card(
                    modifier = Modifier,
                    elevation = CardDefaults
                        .cardElevation(
                            defaultElevation = 20.dp
                        )
                ) {
                    ViewerCanvas(serverCommunicationViewModel = serverViewModel)
                }



                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = serverViewModel.createMaskedWord(serverViewModel.currentWord.value),

                        fontFamily = ov_soge_bold,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 32.dp, bottom = 8.dp, start = 15.dp, end = 15.dp),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                    )
                }


            }
            if (!isWordChosen){
                // for loading other users scores
                while (playerDetailsViewModel.serverCommunicationViewModel?.playersList?.size != playerDetailsViewModel.serverCommunicationViewModel?.playerScoreHashMap?.size) {
                    playerDetailsViewModel.serverCommunicationViewModel?.playersList!!.forEach {
                        if (!playerDetailsViewModel.serverCommunicationViewModel!!.playerScoreHashMap.containsKey(
                                it.name
                            )
                        ) {
                            playerDetailsViewModel.serverCommunicationViewModel!!.playerScoreHashMap.put(
                                it.name,
                                0
                            )
                        }
                    }
                }

                ViewersPopUp(serverViewModel.playerScoreHashMap, roundCount = serverViewModel.room.numberOfRoundsOver.toString())

                LaunchedEffect(Unit){
                    delay(5000)
                    isPopedUp = false
                }
            }
        }

    }


}

@Composable
private fun ViewerCanvas(
    serverCommunicationViewModel: ServerCommunicationViewModel,
    modifier: Modifier = Modifier.fillMaxWidth()
) {


//    var lines = serverCommunicationViewModel.drawingCords
    val lines = serverCommunicationViewModel.drawingCords

//    println(lines + "\nI got some lines ${lines.size}")
    Column(

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.foundation.Canvas(
            modifier = Modifier
                .background(Color.White)
                .width(((LocalConfiguration.current.screenWidthDp) - 20).dp)
                .height(((LocalConfiguration.current.screenWidthDp) - 20).dp)


        ) {

            lines.forEach { line ->
                drawLine(
                    color = line.color,
                    start = line.start,
                    end = line.end,
                    strokeWidth = line.strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            }
            println(lines.size)

        }


    }

}

//
//@Preview(showSystemUi = true)
//@Composable
//fun PreviewGameScreen() {
//    ViewerGameScreen(NavController(LocalContext.current), PlayerDetailsViewModel())
//}