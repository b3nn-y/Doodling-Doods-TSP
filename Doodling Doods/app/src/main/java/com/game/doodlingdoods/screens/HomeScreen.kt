package com.game.doodlingdoods.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.game.doodlingdoods.R


//This is the home screen, where the user either chooses online or local game mode.

@Composable
fun HomeScreen(

    modifier: Modifier = Modifier.padding(8.dp)
) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        NavBar()
        Titlebar()
        PlayOption()
    }
}


@Composable
fun NavBar(modifier: Modifier = Modifier) {
    Box(
        modifier.fillMaxWidth()

    ) {
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.person),
                contentDescription = "person image",
                modifier
                    .padding(8.dp)
                    .size(35.dp)
            )

            Image(
                painter = painterResource(
                    id = R.drawable.stats
                ),
                contentDescription = "person image",
                modifier
                    .padding(8.dp)
                    .size(35.dp)
            )


        }

    }
}

@Composable
fun Titlebar(modifier: Modifier = Modifier) {
    Box(
        modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.title),
            modifier.padding(8.dp),
            fontSize = 30.sp

        )
    }


}

@Composable
fun PlayOption(modifier: Modifier = Modifier) {

        Card(
            modifier.fillMaxWidth()
                .padding(48.dp),

        ) {
            Text(
                text = "Play",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            )
        }

}

@Preview
@Composable
fun PreviewNavbar() {
//    NavBar(modifier = Modifier)
//    Titlebar()
//    PlayOption()
    HomeScreen()
}
