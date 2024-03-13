package com.game.doodlingdoods.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
import com.game.doodlingdoods.ui.theme.DarkBlue

import com.game.doodlingdoods.ui.theme.ov_soge_bold
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel
import com.game.doodlingdoods.viewmodels.ServerCommunicationViewModel
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
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
fun UpdateChat(
    incNum: Int,
    serverViewModel: ServerCommunicationViewModel,
    playerDetailsViewModel: PlayerDetailsViewModel
) {
    ChatBar(serverViewModel, playerDetailsViewModel)
}

@SuppressLint(
    "MutableCollectionMutableState", "UnusedMaterial3ScaffoldPaddingParameter",
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

//    Log.i("Padding",playerDetailsViewModel.paddingSize.toString())

    Scaffold(
        topBar = {
            TopBarCard(backBtnClick = {
                navController.navigate("RoomsEntry")
                playerDetailsViewModel.serverCommunicationViewModel?.closeCommunication()
            }
            )
        },
        bottomBar = {
            if (isWordChosen) {
                UpdateChat(
                    incNum = increasingNumber,
                    serverViewModel = serverViewModel,
                    playerDetailsViewModel = playerDetailsViewModel
                )
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
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = serverViewModel.room.gameMode,
                        fontFamily = ov_soge_bold,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                }




                Text(
                    text = currentWord,
                    fontFamily = ov_soge_bold,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = DarkBlue
                )

                Card(
                    modifier = Modifier.padding(15.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
                ) {
                    DrawingLogicScreen(
                        serverViewModel,
                        playerDetailsViewModel = playerDetailsViewModel
                    )
                }

                ColorBars(playerDetailsViewModel = playerDetailsViewModel)

                Text(
                    text = roomTime,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp
                    )

                )
            }



            if (!isWordChosen && (currentPlayer == playerDetailsViewModel.playerName)) {
                OptionsPopUp(
                    serverViewModel.drawingOptions,
                    serverViewModel,
                    playerDetailsViewModel
                )

                LaunchedEffect(Unit) {
                    delay(5000)

                    if (!isWordChosen) {
                        serverViewModel.sendWord(serverViewModel.room.wordList.random())
                        serverViewModel.isWordChosen.value = true
                    }
//                    if (serverViewModel.userChosenWord == null){
//                        serverViewModel.room.currentWordToGuess = serverViewModel.room.wordList.random()
//                        serverViewModel.sendRoomUpdate()
//                        serverViewModel.isWordChosen.value = true
//                    }
                    Log.i("Words22", serverViewModel.room.currentWordToGuess)
                    isPopedUp = false
                }

            }
        }
    }
}


@SuppressLint("MutableCollectionMutableState")
@Composable
private fun DrawingLogicScreen(
    serverViewModel: ServerCommunicationViewModel,
    playerDetailsViewModel: PlayerDetailsViewModel,
    modifier: Modifier = Modifier.fillMaxWidth()

) {
    var width = LocalConfiguration.current.screenWidthDp - 30
    val lines by remember { mutableStateOf(mutableStateListOf<Line>()) }

    val colorEnvelope by playerDetailsViewModel.currentColor.collectAsState()
    val barColor by playerDetailsViewModel.barColor.collectAsState()


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
                            ),
                            color = if (barColor != null) barColor ?: Color.Black else colorEnvelope?.color
                                ?: Color.Black,
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
                        ((line.start.x) * width),
                        ((line.start.y) * width)
                    ),
                    end = Offset(
                        ((line.end.x) * width),
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
    modifier: Modifier = Modifier,
    playerDetailsViewModel: PlayerDetailsViewModel,


) {
    val colorList = listOf(Black)

    Row(
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        LazyRow(
            modifier.padding(horizontal = 16.dp),

            ) {
            items(items = colorList) { item ->
                RoundBox(color = item,playerDetailsViewModel)
            }
        }

        RoundBoxIcon(viewModel = playerDetailsViewModel)


    }


}

@Composable
fun RoundBox(color: Color,
             viewModel: PlayerDetailsViewModel) {
    val colorList = listOf(Black)
    val changeColor = viewModel.colorBars
    Box(

        modifier = Modifier
            .padding(6.dp)
            .size(40.dp)
            .background(color = color, shape = CircleShape)
            .border(1.dp, Color.Black, shape = CircleShape)
            .clickable {
                if(changeColor[colorList.indexOf(color)]){

                    viewModel.updateBarColor(color = null)
                    viewModel.updateCurrentColor(null)
                }else {
                    viewModel.colorBars[colorList.indexOf(color)] = true
                    viewModel.updateBarColor(color = color)
                    viewModel.updateCurrentColor(null)
                }
            }
    )
}
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun RoundBoxIcon(
    viewModel : PlayerDetailsViewModel
) {
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    if (isSheetOpen){
        ModalBottomSheet(sheetState = sheetState,
            onDismissRequest = {
                isSheetOpen = false
            }) {

            val controller = rememberColorPickerController()

            Column (modifier = Modifier.padding(10.dp)){
                Row (modifier = Modifier.fillMaxWidth()) {
                    AlphaTile(modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .clip(RoundedCornerShape(8.dp)),
                        controller = controller
                    )
                }
                HsvColorPicker(modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(10.dp),
                    controller = controller,
                    onColorChanged = {color ->
                        viewModel.updateCurrentColor(color)
                        viewModel.updateBarColor(color = null)
                    }
                )
                AlphaSlider(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(30.dp),
                    controller = controller
                )
                BrightnessSlider(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(30.dp),
                    controller = controller
                )
            }
        }
    }


    Box(
        modifier = Modifier
            .padding(6.dp)
            .size(40.dp) // Adjust size as needed
            .border(1.dp, Color.Black, shape = CircleShape)
            .clickable {
//                // for color picker
//                ColorPicker()

            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.color_picker_icon),
            contentDescription = "color picker",
            modifier = Modifier
                .size(40.dp)
                .border(1.dp, Color.Black, shape = CircleShape)
                .clickable {
                    isSheetOpen = true
                }
        )
    }
}
@Composable
private fun TopBarCard(
    modifier: Modifier = Modifier,
    backBtnClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp)

    ) {
        Image(
            painter = painterResource(id = R.drawable.back_filled),
            contentDescription = "back",
            modifier = Modifier
                .size(50.dp)
                .padding(8.dp)
                .clickable {
                    backBtnClick()
                }

        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewTest() {
    DrawingScreen(
        navController = NavController(LocalContext.current),
        playerDetailsViewModel = PlayerDetailsViewModel
    )
//    ColorPicker()
}

