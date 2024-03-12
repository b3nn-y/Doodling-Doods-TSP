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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.game.doodlingdoods.R
import com.game.doodlingdoods.filesForServerCommunication.ProfilePics
import com.game.doodlingdoods.ui.theme.DarkGreen
import com.game.doodlingdoods.ui.theme.ov_soge_bold


@Composable
fun UserCard(playerName:String,admin:Boolean,score:Int=0, profile: Int = 0) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(15),
        colors = CardDefaults.cardColors(
            Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,

        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Image(
                painter = painterResource(id = ProfilePics.profiles[profile]?:R.drawable.avatar_dp_1),
                contentDescription = "Player Avatar",
                modifier = Modifier
                    .size(80.dp)
                    .padding(0.dp)
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            )

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,

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
                    fontSize = 24.sp,
                    style = TextStyle(color = Color.Black)
                )

                Text(
                    text = if (admin) {
                        "Admin"
                    } else {
                        "Player"
                    },
                    color = if (admin) {
                        DarkGreen
                    } else {
                        Color.Black
                    },
                    modifier = Modifier
                        .padding(top = 8.dp),
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
                    text = score.toString(),
                    fontSize = 20.sp,
                    fontFamily = ov_soge_bold,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .align(Alignment.End),
                    style = TextStyle(color = Color.Black)
                )
            }

        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview(){
   UserCard(playerName = "Raghu", admin =false )
}


data class PlayerLobbyData(
    var name: String,
    var points: Int,
    var adminState: Boolean,
    var avatar: Int,
)

