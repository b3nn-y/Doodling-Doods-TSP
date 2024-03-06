//package com.game.doodlingdoods.test
//
//import android.annotation.SuppressLint
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.gestures.detectDragGestures
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.ModalBottomSheet
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.paint
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.StrokeCap
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.game.doodlingdoods.R
//import com.game.doodlingdoods.drawingEssentials.Line
//import com.game.doodlingdoods.drawingEssentials.LinesStorage
//import com.game.doodlingdoods.screens.utils.ColorPicker
//import com.game.doodlingdoods.ui.theme.Black
//import com.game.doodlingdoods.ui.theme.GameBlue
//import com.game.doodlingdoods.ui.theme.Green
//import com.game.doodlingdoods.ui.theme.Red
//import com.game.doodlingdoods.ui.theme.Yellow
//import com.game.doodlingdoods.ui.theme.ov_soge_bold
//import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel
//import com.game.doodlingdoods.viewmodels.ServerCommunicationViewModel
//import com.github.skydoves.colorpicker.compose.AlphaSlider
//import com.github.skydoves.colorpicker.compose.AlphaTile
//import com.github.skydoves.colorpicker.compose.BrightnessSlider
//import com.github.skydoves.colorpicker.compose.HsvColorPicker
//import com.github.skydoves.colorpicker.compose.rememberColorPickerController
//import com.google.gson.Gson
//
//@Composable
//fun DrawingScreen(
//    navController: NavController,
//    playerDetailsViewModel: PlayerDetailsViewModel
//) {
//    UserDrawingScreen(navController, playerDetailsViewModel)
//}
//
//@SuppressLint("MutableCollectionMutableState", "UnusedMaterial3ScaffoldPaddingParameter")
//@Composable
//fun UserDrawingScreen(
//    navController: NavController,
//    playerDetailsViewModel: PlayerDetailsViewModel
//) {
//    val serverViewModel = playerDetailsViewModel.serverCommunicationViewModel
//    val state by serverViewModel.state.collectAsState()
//
//    serverViewModel.evaluateServerMessage(state)
//    if (serverViewModel.currentPlayer != playerDetailsViewModel.playerName) {
//        navController.navigate("GameScreen")
//    }
//    Scaffold(
//        bottomBar = { ChatBar() }
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .paint(
//                    painterResource(R.drawable.background), contentScale = ContentScale.FillBounds
//                )
//        ) {
//            Column {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 16.dp, vertical = 8.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = "Game Mode",
//                        fontFamily = ov_soge_bold,
//                        fontSize = 30.sp,
//                        modifier = Modifier.weight(0.8f),
//                        textAlign = TextAlign.Center,
//                        color = GameBlue
//                    )
//                    Image(
//                        painter = painterResource(id = R.drawable.people),
//                        contentDescription = "profile",
//                        modifier = Modifier
//                            .size(100.dp)
//                            .weight(0.2f),
//                        alignment = Alignment.Center,
//                    )
//                }
//
//                Card(
//                    modifier = Modifier.padding(15.dp),
//                    elevation = CardDefaults.cardElevation(
//                        defaultElevation = 20.dp
//                    )
//                ) {
//                    DrawingLogicScreen(serverViewModel)
//                }
//
//                ColorBars(serverViewModel)
//
//                Text(
//                    text = playerDetailsViewModel.randomWord,
//                    fontFamily = ov_soge_bold,
//                    fontSize = 35.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 32.dp),
//                    textAlign = TextAlign.Center,
//                    color = Color.White,
//                )
//            }
//        }
//    }
//}
//
//@SuppressLint("MutableCollectionMutableState")
//@Composable
//private fun DrawingLogicScreen(
//    serverViewModel: ServerCommunicationViewModel, modifier: Modifier = Modifier.fillMaxWidth()
//) {
//
//}
//
//@Composable
//private fun ColorBars(serverViewModel: ServerCommunicationViewModel) {
//    val colorList = listOf(Black, Red, Yellow, Green)
//
//    Row(
//        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
//    ) {
//        LazyRow(
//            modifier = Modifier.padding(horizontal = 16.dp),
//        ) {
//            items(items = colorList) { item ->
//                RoundBox(color = item)
//            }
//        }
//
//        RoundBoxIcon(serverViewModel)
//    }
//}
//
//@Composable
//fun RoundBox(color: Color) {
//    Box(
//        modifier = Modifier
//            .padding(6.dp)
//            .size(40.dp) // Adjust size as needed
//            .background(color = color, shape = CircleShape)
//            .border(4.dp, Color.Black, shape = CircleShape)
//    )
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun RoundBoxIcon(serverViewModel: ServerCommunicationViewModel) {
//    Box(
//        modifier = Modifier
//            .padding(6.dp)
//            .size(40.dp) // Adjust size as needed
//            .border(4.dp, Color.Black, shape = CircleShape)
//            .clickable {
//                serverViewModel.isSheetOpen = true
//            }
//    ) {
//        if (serverViewModel.isSheetOpen) {
//            ModalBottomSheet(
//                sheetState = serverViewModel.sheetState,
//                onDismissRequest = {
//                    serverViewModel.isSheetOpen = false
//                }) {
//
//                val controller = rememberColorPickerController()
//
//                Column(modifier = Modifier.padding(10.dp)) {
//                    Row(modifier = Modifier.fillMaxWidth()) {
//                        AlphaTile(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(40.dp)
//                                .clip(RoundedCornerShape(8.dp)),
//                            controller = controller
//                        )
//                    }
//                    HsvColorPicker(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(300.dp)
//                            .padding(10.dp),
//                        controller = controller,
//                        onColorChanged = { color ->
//                            serverViewModel.colorEnvelope = color
//                        }
//                    )
//                    AlphaSlider(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(10.dp)
//                            .height(30.dp),
//                        controller = controller
//                    )
//                    BrightnessSlider(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(10.dp)
//                            .height(30.dp),
//                        controller = controller
//                    )
//                }
//            }
//        }
//
//        Image(
//            painter = painterResource(id = R.drawable.rainbow_icon),
//            contentDescription = "color picker",
//            modifier = Modifier
//                .size(40.dp)
//                .border(4.dp, Color.Black, shape = CircleShape)
//        )
//    }
//}
//
//@Composable
//fun ChatBar(
//    modifier: Modifier = Modifier,
//) {
//    var message by rememberSaveable {
//        mutableStateOf("")
//    }
//    Row(
//        modifier = modifier
//            .fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center
//    ) {
//        CustomOutlinedTextField(
//            text = message,
//            onValueChange = { message = it },
//            modifier = Modifier
//                .weight(0.9f)
//                .padding(0.dp)
//                .padding(bottom = 8.dp, end = 4.dp, start = 4.dp)
//        )
//
//        IconButton(
//            modifier = Modifier
//                .weight(0.2f)
//                .size(80.dp)
//                .fillMaxSize(),
//            onClick = { }) {
//            Icon(
//                painter = painterResource(id = R.drawable.send),
//                "contentDescription",
//                tint = GameBlue
//            )
//        }
//    }
//}
//
//@Composable
//private fun CustomOutlinedTextField(
//    text: String,
//    onValueChange: (String) -> Unit,
//    modifier: Modifier = Modifier,
//    backgroundColor: Color = Color.White
//) {
//    Surface(
//        shadowElevation = 20.dp,
//        shape = RoundedCornerShape(50),
//        modifier = modifier
//            .fillMaxWidth(0.75f),
//        color = backgroundColor
//    ) {
//        Box(
//            modifier = Modifier.fillMaxWidth(),
//        ) {
//            OutlinedTextField(
//                shape = RoundedCornerShape(50),
//                value = text,
//                onValueChange = onValueChange,
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true,
//                textStyle = TextStyle.Default.copy(fontSize = 20.sp),
//            )
//        }
//    }
//}
//
//@Preview(showSystemUi = true)
//@Composable
//fun PreviewTest() {
//    ColorPicker()
//}
//
//@Preview(
//    showSystemUi = true,
//    showBackground = true,
//)
//@Composable
//fun PreviewDrawingScreen() {
//    // Preview code here...
//}