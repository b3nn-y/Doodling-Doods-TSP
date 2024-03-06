package com.game.doodlingdoods.screens

import android.annotation.SuppressLint
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

// this is the ongoing game screen, where the live drawing is shown, along with hints, chat, players and their scores, timer etc.
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ViewerGameScreen(navController: NavController, playerDetailsViewModel: PlayerDetailsViewModel) {
    val serverViewModel = playerDetailsViewModel.serverCommunicationViewModel
    val state by serverViewModel.state.collectAsState()

    serverViewModel.evaluateServerMessage(state)
    if (serverViewModel.currentPlayer == playerDetailsViewModel.playerName) {
        navController.navigate("DrawingScreen")
    }
    Scaffold(
        //re used our existing chat bar in drawing screen
        bottomBar = { ChatBar() }
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
                        text = "Game Mode",
                        fontFamily = ov_soge_bold,
                        fontSize = 30.sp,
                        modifier = Modifier.weight(0.8f),

                        textAlign = TextAlign.Center,
                        color = GameBlue

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
                    text = "${playerDetailsViewModel.currentPlayer}'s turn",
                    modifier = Modifier.padding(8.dp),
                    fontFamily = ov_soge_bold,
                    fontSize = 30.sp,


                    textAlign = TextAlign.Center,
                    color = GameBlue

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



                Text(
                    text = playerDetailsViewModel.guessWord,
                    fontFamily = ov_soge_bold,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, bottom = 8.dp),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                )
                
                Text(
                    text = "10:00",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,

                )


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