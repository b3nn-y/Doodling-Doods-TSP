package com.game.doodlingdoods.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.game.doodlingdoods.drawingEssentials.Line
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel
import com.game.doodlingdoods.viewmodels.ServerCommunicationViewModel

// this is the ongoing game screen, where the live drawing is shown, along with hints, chat, players and their scores, timer etc.
@Composable
fun GameScreen(navController: NavHostController, playerDetailsViewModel: PlayerDetailsViewModel) {
    var serverViewModel = playerDetailsViewModel.serverCommunicationViewModel
    val state by serverViewModel.state.collectAsState()
    val isConnecting by serverViewModel.isConnecting.collectAsState()
    val showConnectionError by serverViewModel.showConnectionError.collectAsState()
    serverViewModel.evaluateServerMessage(state)
    if (serverViewModel.currentPlayer == playerDetailsViewModel.playerName){
        navController.navigate("DrawingScreen")
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(serverViewModel)
    }


}

@Composable
private fun Canvas(
    serverCommunicationViewModel: ServerCommunicationViewModel,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp / 2
    val width = configuration.screenWidthDp

//    var lines = serverCommunicationViewModel.drawingCords
    val lines = serverCommunicationViewModel.drawingCords

    println(lines + "\nI got some lines ${lines.size}")
    Column {
        androidx.compose.foundation.Canvas(
            modifier = Modifier
                .background(Color.Cyan)
                .width(((LocalConfiguration.current.screenWidthDp) - 20).dp)
                .height(((LocalConfiguration.current.screenWidthDp) - 20).dp)
                .padding(20.dp)

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
        Spacer(modifier = Modifier.height(30.dp))

    }

}


@Preview(showSystemUi = true)
@Composable
fun PreviewGameScreen() {
//    GameScreen(navController)
}