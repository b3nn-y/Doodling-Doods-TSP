package com.game.doodlingdoods.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.game.doodlingdoods.R
import com.game.doodlingdoods.viewmodels.DashBoardViewModel
import retrofit2.http.Body
import java.nio.file.WatchEvent

@Composable
fun DashBoardScreen(
    navController: NavController,

) {
    val dashBoardViewModel = viewModel<DashBoardViewModel>()
    dashBoardViewModel.getLeaderBoardDetails()
    DashBoard(navController=navController, dashBoardViewModel = dashBoardViewModel)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun DashBoard(
    modifier: Modifier = Modifier,
    navController: NavController,
    dashBoardViewModel: DashBoardViewModel
) {
    val testList = hashMapOf<String,Int>()
    testList.put("Raghu",999)
    testList.put("Ragh",999)
    testList.put("Raghur",999)
    testList.put("Raghuram",9)
    Scaffold(
        modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_gradient_blue),
            contentDescription = "background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back_filled),
                    contentDescription = "back btn",
                    modifier
                        .size(45.dp)
                        .clickable {
                            navController.navigate("RoomsEntry")
                        }
                )
                Text(
                    text = "LeaderBoard",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Body(playerScoreDesc =dashBoardViewModel.leaderBoardHashMap )
        }
    }
}

@Composable
private fun Body(
    modifier: Modifier = Modifier,
    playerScoreDesc: Map<String, Int>,
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxHeight(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
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

@Preview(showSystemUi = true)
@Composable
fun PreviewDashBoard() {
    val context = LocalContext.current
    DashBoardScreen(navController = NavController(context))
}