package com.game.doodlingdoods.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
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
import com.game.doodlingdoods.drawingEssentials.Line
import com.game.doodlingdoods.drawingEssentials.LinesStorage
import com.game.doodlingdoods.screens.utils.ChatBar
import com.game.doodlingdoods.screens.utils.OptionsPopUp
import com.game.doodlingdoods.ui.theme.Black
import com.game.doodlingdoods.ui.theme.Green
import com.game.doodlingdoods.ui.theme.Red
import com.game.doodlingdoods.ui.theme.Yellow
import com.game.doodlingdoods.ui.theme.ov_soge_bold
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel
import com.game.doodlingdoods.viewmodels.ServerCommunicationViewModel
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


@Composable
fun DrawingScreen(
    navController: NavController, playerDetailsViewModel: PlayerDetailsViewModel
) {
    UserDrawingScreen(navController, playerDetailsViewModel)

}


@Composable
fun UpdateChat(incNum: Int, serverViewModel: ServerCommunicationViewModel, playerDetailsViewModel: PlayerDetailsViewModel){
    ChatBar(serverViewModel, playerDetailsViewModel)
}

@SuppressLint("MutableCollectionMutableState", "UnusedMaterial3ScaffoldPaddingParameter",
    "StateFlowValueCalledInComposition"
)
@Composable
fun UserDrawingScreen(
    navController: NavController,
    playerDetailsViewModel: PlayerDetailsViewModel
) {
    val serverViewModel = playerDetailsViewModel.serverCommunicationViewModel!!
    val state by serverViewModel.state.collectAsState()
    val roomTime by serverViewModel.roomTime.collectAsState()
    val currentWord by serverViewModel.currentWord.collectAsState()
    val currentPlayer by serverViewModel.currentPlayer.collectAsState()
    val roundsPlayed by serverViewModel.roundsPlayed.collectAsState()
    val increasingNumber by serverViewModel.increasingNumber.collectAsState()

    var isPopedUp by rememberSaveable {
        mutableStateOf(true)
    }
    var isWordChosen by serverViewModel.isWordChosen

    serverViewModel.evaluateServerMessage(state)

    if (serverViewModel.room.gameOver) {
        navController.navigate("LeaderBoardScreen")
    }

    if (currentPlayer != playerDetailsViewModel.playerName) {
        navController.navigate("GameScreen")
    }

    Scaffold(
        bottomBar = {
            if (isWordChosen){
                UpdateChat(incNum = increasingNumber, serverViewModel = serverViewModel, playerDetailsViewModel = playerDetailsViewModel)
            }

        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(R.drawable.background_gradient_blue),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = serverViewModel.room.gameMode,
                        fontFamily = ov_soge_bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                    Image(
                        painter = painterResource(id = R.drawable.people),
                        contentDescription = "profile",
                        modifier = Modifier
//                            .clickable {
//                                isPopedUp = !isPopedUp
//                            }
                            .size(60.dp),
                        alignment = Alignment.Center
                    )
                }

                Text(
                    text = roomTime,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                Text(
                    text = currentWord,
                    fontFamily = ov_soge_bold,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )

                Card(
                    modifier = Modifier.padding(15.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
                ) {
                    DrawingLogicScreen(serverViewModel, playerDetailsViewModel = playerDetailsViewModel)
                }

                ColorBars()
            }

            if (!isWordChosen && (currentPlayer == playerDetailsViewModel.playerName)) {
                OptionsPopUp(serverViewModel.drawingOptions,serverViewModel, playerDetailsViewModel)

                LaunchedEffect(Unit){
                    delay(5000)

                    if (!isWordChosen){
                        serverViewModel.sendWord(serverViewModel.room.wordList.random())
                        serverViewModel.isWordChosen.value = true
                    }
//                    if (serverViewModel.userChosenWord == null){
//                        serverViewModel.room.currentWordToGuess = serverViewModel.room.wordList.random()
//                        serverViewModel.sendRoomUpdate()
//                        serverViewModel.isWordChosen.value = true
//                    }
                    Log.i("Words22",serverViewModel.room.currentWordToGuess)
                    isPopedUp=false
                }

            }
        }
    }
}


@SuppressLint("MutableCollectionMutableState")
@Composable
private fun DrawingLogicScreen(
    serverViewModel: ServerCommunicationViewModel,playerDetailsViewModel: PlayerDetailsViewModel, modifier: Modifier = Modifier.fillMaxWidth()

) {
    var width = LocalConfiguration.current.screenWidthDp - 30
    val lines by remember { mutableStateOf(mutableStateListOf<Line>()) }
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(
            modifier
                .background(Color.White)
                .width(((LocalConfiguration.current.screenWidthDp) - 20).dp)
                .height(((LocalConfiguration.current.screenWidthDp) - 20).dp)
                .padding(horizontal = 20.dp)
                .pointerInput(true) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()

                        val line = Line(
                            start = Offset(
                                (change.position - dragAmount).x / width,
                                ((change.position - dragAmount).y) / width
                            ),
                            end = Offset(
                                ((change.position.x) / width),
                                (((change.position.y) / width))
                            )
                        )


                        lines.add(line)
                        println(lines)
                        serverViewModel.room.cords = Gson().toJson(LinesStorage(lines))
                        playerDetailsViewModel.serverCommunicationViewModel!!.room.players.forEach {
                            if (it.name == playerDetailsViewModel.playerName) {
                                it.score =
                                    playerDetailsViewModel.serverCommunicationViewModel!!.score
                                playerDetailsViewModel.serverCommunicationViewModel!!.sendRoomUpdate()
                            }
                        }
                        serverViewModel.sendRoomUpdate()

                    }
                }) {

            lines.forEach { line ->
                drawLine(
                    color = line.color,
                    start = Offset(
                        ((line.start.x) * width ) ,
                        ((line.start.y) * width )
                    ),
                    end = Offset(
                        ((line.end.x) * width ) ,
                        ((line.end.y) * width)
                    ),
                    strokeWidth = line.strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            }
            println(lines.size)

        }


    }
}

@Composable
private fun ColorBars(
    modifier: Modifier = Modifier


) {
    val colorList = listOf(Black, Red, Yellow, Green)

    Row(
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        LazyRow(
            modifier.padding(horizontal = 16.dp),

            ) {
            items(items = colorList) { item ->
                RoundBox(color = item)
            }
        }

        RoundBoxIcon()
//        Image(
//            painter = painterResource(id = R.drawable.paint_brush),
//            contentDescription = "paint_brush",
//            modifier.size(40.dp)
//        )

    }


}

@Composable
fun RoundBox(color: Color) {
    Box(

        modifier = Modifier

            .padding(6.dp)
            .size(40.dp) // Adjust size as needed
            .background(color = color, shape = CircleShape)
            .border(4.dp, Color.Black, shape = CircleShape)
    )
}

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun RoundBoxIcon() {
    var colorEnvelope by rememberSaveable {
        mutableStateOf<ColorEnvelope?>(null)
    }
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    Box(

        modifier = Modifier

            .padding(6.dp)
            .size(40.dp) // Adjust size as needed
            .border(4.dp, Color.Black, shape = CircleShape)
            .clickable {
//                // for color picker
//                ColorPicker()

            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.rainbow_icon),
            contentDescription = "color picker",
            modifier = Modifier
                .size(40.dp)

                .border(4.dp, Color.Black, shape = CircleShape)

        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewTest() {
    DrawingScreen(navController = NavController(LocalContext.current), playerDetailsViewModel = PlayerDetailsViewModel)
//    ColorPicker()
}

