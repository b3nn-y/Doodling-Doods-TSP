package com.game.doodlingdoods.screens.utils

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.game.doodlingdoods.R

import com.game.doodlingdoods.ui.theme.GameLightBlue
import com.game.doodlingdoods.ui.theme.ov_soge_bold
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel
import com.game.doodlingdoods.viewmodels.ServerCommunicationViewModel

@Composable
fun OptionsPopUp(
    words: ArrayList<String>,
    serverCommunicationViewModel: ServerCommunicationViewModel,
    playerViewModel: PlayerDetailsViewModel,
    modifier: Modifier = Modifier
        .fillMaxSize(),
) {
    var currentPlayer = serverCommunicationViewModel.currentPlayer.collectAsState()

    val progressValue = 0f
    val infiniteTransition = rememberInfiniteTransition()
    val progressAnimationValue by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = progressValue,
        animationSpec = infiniteRepeatable(animation = tween(5000)), label = ""
    )

    if (currentPlayer.value == playerViewModel.playerName) {
        Image(
            painter = painterResource(id = R.drawable.background_gradient_blue),
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxHeight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 10.dp
                ),
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxHeight(0.55f)
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Choose a Word",
                        fontFamily = ov_soge_bold,
                        fontSize = 30.sp
                    )

                    LinearProgressIndicator(
                        modifier = Modifier
                            .padding(20.dp)
                            .height(15.dp),
                        color = GameLightBlue,
                        trackColor = Color.White,
                        strokeCap = StrokeCap.Round,
                        progress = progressAnimationValue
                    )

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxHeight(0.7f)
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Card(
                                modifier = Modifier
                                    .clickable {
                                        serverCommunicationViewModel.userChosenWord = words[0]
                                        serverCommunicationViewModel.sendWord(words[0])
                                    },
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 10.dp,
                                    pressedElevation = 10.dp
                                )
                            ) {
                                Text(
                                    text = words[0],
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    fontFamily = ov_soge_bold,
                                    modifier = Modifier
                                        .padding(20.dp)
                                )
                            }

                            Card(
                                modifier = Modifier
                                    .clickable {
                                        serverCommunicationViewModel.userChosenWord = words[1]
                                        serverCommunicationViewModel.sendWord(words[1])
                                    },
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 10.dp,
                                    pressedElevation = 10.dp
                                )
                            ) {
                                Text(
                                    text = words[1],
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    fontFamily = ov_soge_bold,
                                    modifier = Modifier
                                        .padding(20.dp)
                                )
                            }
                        }

                        Card(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 30.dp)
                                .clickable {
                                    serverCommunicationViewModel.userChosenWord = words[2]
                                    serverCommunicationViewModel.sendWord(words[2])
                                },
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 10.dp,
                                pressedElevation = 10.dp
                            )
                        ) {
                            Text(
                                text = words[2],
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontFamily = ov_soge_bold,
                                modifier = Modifier
                                    .padding(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ViewersPopUp(
    playerScore: Map<String, Int>,
    roundCount: String,
    profiles: HashMap<String, Int>,
    modifier: Modifier = Modifier
        .fillMaxSize(),
) {
    Log.i("Hashmap55", playerScore.toString())
    Log.i("Hashmap66", profiles.toString())
    ScoreCard(playerScoreDesc = playerScore, roundCount = roundCount, profiles = profiles)


}

@Composable
private fun ScoreCard(
    modifier: Modifier = Modifier,
    playerScoreDesc: Map<String, Int>,
    profiles: HashMap<String, Int>,
    roundCount: String,
) {


    val progressValue = 0f
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val progressAnimationValue by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = progressValue,
        animationSpec = infiniteRepeatable(animation = tween(5000)), label = ""
    )
    val playerScores = playerScoreDesc.toList()
        .sortedByDescending { it.second }
        .toMap()
    Image(
        painter = painterResource(id = R.drawable.background_gradient_blue),
        contentDescription = "Background Image",
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {


        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),

            modifier = Modifier
                .padding(8.dp)
                .fillMaxHeight(0.7f),

            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth(),

                ) {
                Text(
                    text = "Leader Board",
                    fontSize = 28.sp,
                    modifier = Modifier
                        .padding(8.dp),
                    fontWeight = FontWeight.Bold
                )

            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Text(
                    text = "Rounds: ${roundCount}/5",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(8.dp),
                    fontWeight = FontWeight.Bold
                )
                LinearProgressIndicator(
                    modifier = Modifier
                        .padding(10.dp)
                        .height(15.dp),
                    color = GameLightBlue,
                    trackColor = Color.White,
                    strokeCap = StrokeCap.Round,
                    progress = progressAnimationValue
                )




                LazyColumn(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp)),
                ) {
                    items(playerScores.keys.toMutableList()) { player ->
                        com.game.doodlingdoods.screens.UserCard(
                            playerName = player,
                            score = playerScoreDesc[player] ?: 0,
                            profile = profiles
                        ) // re used from utils
                    }
                }
            }
        }
    }


}


@Composable
fun HintPopup(
    closeButton:()->Unit,
    visible:Boolean,
    modifier: Modifier = Modifier,
) {

    var isVisible = visible

    AnimatedVisibility(visible =isVisible ) {
        Image(
            painter = painterResource(id = R.drawable.background_gradient_blue),
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {


            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),

                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight(0.7f),

                ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = modifier
                            .padding(8.dp)
                            .fillMaxWidth(),

                        ) {
                        Text(
                            text = "How to play",
                            fontSize = 28.sp,
                            modifier = Modifier
                                .weight(0.9f)
                                .padding(start = 50.dp),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                        Image(
                            painter = painterResource(id = R.drawable.close_icon),
                            contentDescription = "close",
                            modifier
                                .align(Alignment.CenterVertically)
                                .weight(0.2f)
                                .size(50.dp)
                                .clickable {
                                    closeButton()
                                    isVisible =false
                                }
                        )

                    }

                    Text(
                        text = "Welcome to Doodling Doods",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(8.dp)
                    )

                    Text(
                        text = "How the game works?",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp)
                    )

                    Text(
                        text = "There will be 3 rounds" +
                                "each player gets 3 chances to draw the selected word" +
                                "while others try to guess it" +
                                "you can guess it in our interactive chat bar where you can communicate with other players and try win" +
                                "points by guessing the correct word",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(4.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "How to start a game?",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = "To play first create or join a game by entering it's room codes in the respective screens" +
                                "or you can join ongoing game from the public rooms",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(4.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewPopup() {
//    OptionsPopUp(words = arrayListOf("Word1","Word2","Word3",),PlayerDetailsViewModel.serverCommunicationViewModel)
    val map = hashMapOf<String, Int>()
    map.put("Raghu", 99)
    map.put("master", 999)
//    ViewersPopUp(map, roundCount = "4", profiles = hashMapOf())

    HintPopup(closeButton = {},visible = true)

}