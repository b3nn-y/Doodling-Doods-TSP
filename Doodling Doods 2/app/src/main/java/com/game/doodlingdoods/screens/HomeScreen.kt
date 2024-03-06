package com.game.doodlingdoods.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.game.doodlingdoods.R


//This is the home screen, where the user either chooses online or local game mode.

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){

        Image(painter = painterResource(id = R.drawable.background),
            contentDescription = "bg image",
            contentScale = ContentScale.FillBounds,
            modifier = modifier.fillMaxSize())

        Column(
            modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            NavBar()
            Titlebar()

            PlayOption(
                PlayButtonClick = {
                    navController.navigate("AccountSetup")
                }
            )
        }
    }
}

// navbar contains user profile and their stats
@Composable
fun NavBar(modifier: Modifier = Modifier) {
    Box(
        modifier.fillMaxWidth()

    ) {
        Row(
            modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {}
    }
}

//title bar for game title it appears on the middle of the screen
@Composable
fun Titlebar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        val infiniteTransition = rememberInfiniteTransition()

        val translationY by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 10f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 1500
                    0f at 750
                    10f at 1500
                },
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )

        val scale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.5f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 3000
                    0.85f at 1500
                    1f at 3000
                },
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )

        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "logo",
            modifier = Modifier
                .offset(y = translationY.dp)
                .scale(scale)
        )
    }
}

//play button
@Composable
fun PlayOption(
    modifier: Modifier = Modifier,
    PlayButtonClick: () -> Unit
) {
    var scale by remember {
        mutableStateOf(1f)
    }

    val animation = rememberInfiniteTransition()
    val interactionSource = remember { MutableInteractionSource() }

    scale = animation.animateFloat(
        initialValue = 1f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 3000
                0.85f at 1500
                1f at 3000
            },
            repeatMode = RepeatMode.Reverse
        ), label = ""
    ).value

    Box(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                PlayButtonClick()
            }
            .padding(4.dp)
            .padding(vertical = 32.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.play_button),
            contentDescription = "Play!",
            modifier = Modifier
                .padding(4.dp)
                .scale(scale)
        )
    }
}

@Preview
@Composable
fun PreviewNavbar() {
    HomeScreen(navController = NavController(LocalContext.current))
}
