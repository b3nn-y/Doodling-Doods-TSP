package com.game.doodlingdoods.screens
//This screen shows the global leaderboard and the player's stats


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.game.doodlingdoods.R
import com.game.doodlingdoods.ui.theme.ov_soge_bold
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel

//This screen shows the global leaderboard and the player's stats
@Composable
fun LeaderBoardScreen(
    navController: NavController,
    playerViewModel: PlayerDetailsViewModel
) {

    LeaderBoard(
        navController = navController,
        playerViewModel = playerViewModel,

        )

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun LeaderBoard(
    modifier: Modifier = Modifier,
    navController: NavController,
    playerViewModel: PlayerDetailsViewModel
) {

    val goldMedal by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.gold_medal))
    val silverMedal by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.silver_medal))
    val bronzeMedal by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.bronze_medal))
//    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        topBar = {
            TopBar(backBtnClick = {
                navController.navigate("RoomsEntry")
            })
        },
        bottomBar = { BottomBar() },
    ) {
        // for loading other users scores
        while (playerViewModel.serverCommunicationViewModel?.playersList?.size != playerViewModel.serverCommunicationViewModel?.playerScoreHashMap?.size) {
            playerViewModel.serverCommunicationViewModel?.playersList!!.forEach {
                if (!playerViewModel.serverCommunicationViewModel!!.playerScoreHashMap.containsKey(
                        it.name
                    )
                ) {
                    playerViewModel.serverCommunicationViewModel!!.playerScoreHashMap.put(
                        it.name,
                        0
                    )
                }
            }
        }

        val serverViewModel = playerViewModel.serverCommunicationViewModel

        Image(
            painter = painterResource(id = R.drawable.background_gradient_blue),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Spacer(modifier.height(50.dp))

            Card(

                modifier = Modifier,
                colors = CardDefaults.cardColors(
                    Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 20.dp,
                    disabledElevation = 20.dp
                ),
                shape = RoundedCornerShape(10)
            ) {

                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(bottom = 20.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 50.dp)
                    ) {
                        LottieAnimation(

                            composition = bronzeMedal,
                            iterations = LottieConstants.IterateForever,
                            modifier = Modifier
                                .size(120.dp)

                        )

                        Image(
                            alignment = Alignment.Center,
                            painter = painterResource(id = R.drawable.avatar1),
                            contentDescription = "3rd",
                            modifier = Modifier
                                .size(80.dp)
                        )

                        Text(
                            text = "Player 3",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = ov_soge_bold,
                            modifier = Modifier
                                .padding(top = 10.dp),
                            textAlign = TextAlign.Center
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                alignment = Alignment.Center,
                                painter = painterResource(id = R.drawable.trophy),
                                contentDescription = "Points Image",
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.Bottom)
                            )
                            Text(
                                text = "522",
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontFamily = ov_soge_bold,
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    .align(Alignment.Bottom),
                                textAlign = TextAlign.Center
                            )
                        }

                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LottieAnimation(
                            composition = goldMedal,
                            iterations = LottieConstants.IterateForever,
                            modifier = Modifier
                                .size(110.dp)
                        )

                        Image(
                            alignment = Alignment.Center,
                            painter = painterResource(id = R.drawable.avatar1),
                            contentDescription = "1st",
                            modifier = Modifier
                                .size(80.dp),

                            )

                        Text(
                            text = "Player 1",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = ov_soge_bold,
                            modifier = Modifier
                                .padding(top = 10.dp),
                            textAlign = TextAlign.Center
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                alignment = Alignment.Center,
                                painter = painterResource(id = R.drawable.trophy),
                                contentDescription = "Points Image",
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.Bottom)
                            )
                            Text(
                                text = "696",
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontFamily = ov_soge_bold,
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    .align(Alignment.Bottom),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 50.dp)
                    ) {

                        LottieAnimation(
                            composition = silverMedal,
                            iterations = LottieConstants.IterateForever,
                            modifier = Modifier
                                .size(120.dp)
                        )
                        Image(
                            alignment = Alignment.Center,
                            painter = painterResource(id = R.drawable.avatar1),
                            contentDescription = "2nd",
                            modifier = Modifier
                                .size(80.dp)
                        )

                        Text(
                            text = "Player 2",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = ov_soge_bold,
                            modifier = Modifier
                                .padding(top = 10.dp),
                            textAlign = TextAlign.Center
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                alignment = Alignment.Center,
                                painter = painterResource(id = R.drawable.trophy),
                                contentDescription = "Points Image",
                                modifier = Modifier
                                    .height(20.dp)
                                    .align(Alignment.Bottom)
                            )
                            Text(
                                text = "578",
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontFamily = ov_soge_bold,
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    .align(Alignment.Bottom),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            Spacer(modifier.height(30.dp))

            playerViewModel.serverCommunicationViewModel?.let { it1 ->
                Body(
                    playerScoreDesc = playerViewModel.serverCommunicationViewModel!!.playerScoreHashMap,
                )
            }

        }
    }
}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    backBtnClick: () -> Unit
) {
//    Column {
    Row(
        modifier
            .fillMaxWidth()
            .padding(0.dp)
            .padding(top = 16.dp, bottom = 30.dp, start = 8.dp, end = 8.dp),

        ) {
        Image(
            painter = painterResource(id = R.drawable.back_filled),
            contentDescription = "back",
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    backBtnClick()
                }

        )

        Text(
            text = "Leader Board",
            color = Color.White,
            fontSize = 30.sp,
            fontFamily = ov_soge_bold,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.Center
        )
    }

}

@Composable
private fun Body(
    modifier: Modifier = Modifier,
    playerScoreDesc: Map<String, Int>,
) {

//    val playerNames = listOf("player1", "player2", "player3", "player4", "player5","player1", "player2", "player3", "player4", "player5")

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
        ) {
            items(playerScoreDesc.keys.toMutableList()) { player ->
                UserCard(
                    playerName = player,
                    score = playerScoreDesc[player] ?: 0
                ) // re used from utils
            }
        }
    }

}


@Composable
fun UserCard(
    playerName: String,
    score: Int
//    playerPosition : Int,
//    playerPoints : Int)
) {
    Card(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12),
        colors = CardDefaults.cardColors(
            Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
            pressedElevation = 10.dp
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {

//            Text(
//                text = "4",
//                fontSize = 30.sp,
//                modifier = Modifier.padding(10.dp)
//            )

            Image(
                painter = painterResource(id = R.drawable.avatar1),
                contentDescription = "Player Avatar",
                modifier = Modifier
                    .size(80.dp)
                    .padding(0.dp)
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            )

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
            )
            {

                Text(
                    text = playerName,
                    modifier = Modifier,
                    fontFamily = ov_soge_bold,
                    fontSize = 24.sp
                )
            }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .padding(8.dp)
                    .padding(end = 10.dp)
                    .fillMaxWidth()
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.trophy),
                    contentDescription = "Trophy",
                    modifier = Modifier
                )

                Text(
                    text = score.toString(),
                    fontSize = 24.sp,
                    fontFamily = ov_soge_bold,
                    modifier = Modifier
                        .padding(start = 10.dp)
                )
            }

        }

    }
}

@Composable
private fun BottomBar() {

}

@Preview(showSystemUi = true)
@Composable
fun PreviewUserCard() {
    Column {
        UserCard(playerName = "Raghu", score =999 )
        UserCard(playerName = "Ram", score =999 )
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewLeaderBoardScreen() {
    LeaderBoardScreen(navController = NavController(LocalContext.current), PlayerDetailsViewModel)
//    UserCard(playerName = "Vijay")
}