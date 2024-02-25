package com.game.doodlingdoods.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.game.doodlingdoods.drawingEssentials.Line
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel
import kotlinx.coroutines.launch

@Composable
fun DrawingScreen(navController: NavHostController, playerDetailsViewModel: PlayerDetailsViewModel){
    UserDrawingScreen(navController , playerDetailsViewModel)
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun UserDrawingScreen(navController: NavHostController ,playerDetailsViewModel: PlayerDetailsViewModel){
    var serverViewModel = playerDetailsViewModel.serverCommunicationViewModel
    val state by serverViewModel.state.collectAsState()
    val isConnecting by serverViewModel.isConnecting.collectAsState()
    val showConnectionError by serverViewModel.showConnectionError.collectAsState()
    serverViewModel.evaluateServerMessage(state)
    if (serverViewModel.currentPlayer != playerDetailsViewModel.playerName){
        navController.navigate("GameScreen")
    }

    var lines by remember { mutableStateOf(mutableStateListOf<Line>()) }
    Surface {
        Column() {

            Column {
                androidx.compose.foundation.Canvas(
                    modifier = Modifier
                        .background(Color.LightGray)
                        .width(((LocalConfiguration.current.screenWidthDp) - 20).dp)
                        .height(((LocalConfiguration.current.screenWidthDp) - 20).dp)
                        .padding(20.dp)
                        .pointerInput(true) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()

                                val line = Line(
                                    start = change.position - dragAmount,
                                    end = change.position,
                                )


                                lines.add(line)


                            }
                        }
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
    }
}