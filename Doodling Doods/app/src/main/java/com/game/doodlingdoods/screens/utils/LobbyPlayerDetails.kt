package com.game.doodlingdoods.screens.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.game.doodlingdoods.R
import com.game.doodlingdoods.ui.theme.DarkGreen
import com.game.doodlingdoods.ui.theme.ov_soge_bold

//@Composable
//fun LobbyPlayerDetails(playerData: PlayerLobbyData) {
//
//    val playerDataList = arrayListOf(
//        PlayerLobbyData("Vijay", 27, true, R.drawable.avatar1),
//        PlayerLobbyData("Ben", 89, false, R.drawable.avatar1),
//        PlayerLobbyData("Bala", 69, false, R.drawable.avatar1),
//        PlayerLobbyData("is", 35, false, R.drawable.avatar1),
//        PlayerLobbyData("Gay", 74, false, R.drawable.avatar1),
//        PlayerLobbyData("Raghu", 99, false, R.drawable.avatar1),
//        PlayerLobbyData("Jose", 76, false, R.drawable.avatar1),
//        PlayerLobbyData("Nira", 65, false, R.drawable.avatar1),
//        PlayerLobbyData("Chan", 23, false, R.drawable.avatar1),
//        PlayerLobbyData("Mark", 90, false, R.drawable.avatar1),
//    )
//    PlayerCards(playerDataList)
//
//}
//
//
//@Composable
//private fun PlayerCards(playerDataList: ArrayList<PlayerLobbyData>) {
//    LazyColumn{
//        items(playerDataList){ player->
//            UserCard(playerData = player)
//        }
//    }
//}
@Composable
fun UserCard(playerName:String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(50),
        colors = CardDefaults.cardColors(
            Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
            pressedElevation = 10.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {

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
                    modifier = Modifier
                        .padding(top = 8.dp),
                    fontFamily = ov_soge_bold,
                    fontSize = 24.sp
                )

                Text(
                    text = if (true) {
                        "Admin"
                    } else {
                        "Player"
                    },
                    color = if (true) {
                        DarkGreen
                    } else {
                        Color.Black
                    },
                    modifier = Modifier
                        .padding(10.dp),
                    fontFamily = ov_soge_bold,
                    fontSize = 16.sp
                )
            }


            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(8.dp)
                    .padding(end = 10.dp)
                    .fillMaxWidth()
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.trophy),
                    contentDescription = "Trophy",
                    modifier = Modifier.align(Alignment.End)
                )

                Text(
                    text = "0",
                    fontSize = 20.sp,
                    fontFamily = ov_soge_bold,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .align(Alignment.End)
                )
            }

        }

    }
}

//@Preview(showSystemUi = true)
//@Composable
//fun Preview(){
//    LobbyPlayerDetails(playerData = PlayerLobbyData("Vijay", 27, true, R.drawable.avatar1) )
//}


data class PlayerLobbyData(
    var name: String,
    var points: Int,
    var adminState: Boolean,
    var avatar: Int,
)

