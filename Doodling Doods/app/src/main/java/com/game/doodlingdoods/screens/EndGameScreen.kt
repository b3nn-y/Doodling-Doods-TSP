package com.game.doodlingdoods.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.game.doodlingdoods.R
import com.game.doodlingdoods.screens.utils.UserCard
import com.game.doodlingdoods.ui.theme.ov_soge_bold

//This screen is shown as soon as a game is over and shows the top scores of the current players and their stats.
@Composable
fun EndGameScreen() {


    GameOver()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun GameOver(modifier: Modifier = Modifier) {

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomBar()}
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
                Body()
            }
        }
    }


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
            text = "Game Over",
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
    modifier: Modifier = Modifier
) {

    val playerNames = listOf("player1", "player2", "player3", "player4", "player5","player1", "player2", "player3", "player4", "player5")
    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier

            .fillMaxSize()
            .background(Color.White)
    ) {
        items(playerNames) { player ->
            UserCard(playerName = player) // re used from utils
        }
    }

}

@Composable
private fun BottomBar (
    modifier: Modifier=Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.return_to_lobby),
        contentDescription ="Return to lobby",
        modifier.fillMaxWidth()
    )
}

@Preview(showSystemUi = true)
@Composable
fun PrevEndGame() {
    EndGameScreen()
}