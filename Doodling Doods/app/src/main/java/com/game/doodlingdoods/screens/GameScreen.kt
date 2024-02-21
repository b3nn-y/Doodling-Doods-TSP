package com.game.doodlingdoods.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

// this is the ongoing game screen, where the live drawing is shown, along with hints, chat, players and their scores, timer etc.
@Composable
fun GameScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas()
    }


}

@Composable
private fun Canvas(
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp / 2
    val width = configuration.screenWidthDp

    Card(
        modifier = modifier
            .height(height.dp)
            .width(width.dp)
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )


    ) {

    }

}


@Preview(showSystemUi = true)
@Composable
fun PreviewGameScreen() {
//    GameScreen(navController)
}