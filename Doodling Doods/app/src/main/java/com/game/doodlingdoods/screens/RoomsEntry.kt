package com.game.doodlingdoods.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel
import com.game.doodlingdoods.viewmodels.ServerCommunicationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//This screen shows the options to join a existing room or create a new room for others to join.

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RoomsEntryScreen(
    navController: NavController,
    playerDetailsViewModel: PlayerDetailsViewModel
) {




    JoinGames(
        createRoomButtonClick = {
            playerDetailsViewModel.joinType = "create"
            navController.navigate("CreateRoom")
            println("create room btn")
        },
        joinRoomButtonClick = {
            playerDetailsViewModel.joinType = "join"
            navController.navigate("JoinRoom")
            println("Join room btn")
        }
    )

}

@Composable
private fun JoinGames(
    modifier: Modifier = Modifier,
    createRoomButtonClick: () -> Unit,
    joinRoomButtonClick: () -> Unit
) {
    Surface(
        modifier.fillMaxSize(),
        shadowElevation = 40.dp
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Button(
                    onClick = {
                        joinRoomButtonClick()
                    },
                    modifier

                        .padding(4.dp)
                        .padding(8.dp)

                ) {
                    Text(
                        text = "Join Room",
                        fontSize = 22.sp
                    )
                }
                Button(
                    onClick = {
                        createRoomButtonClick()
                    },
                    modifier

                        .padding(4.dp)
                        .padding(8.dp)

                ) {
                    Text(
                        text = "Create Room",
                        fontSize = 22.sp
                    )
                }

                //below button for find ongoing rooms

//                Button(
//                    onClick = { /*TODO*/ },
//                    modifier
//                        .padding(4.dp)
//                        .padding(8.dp)
//
//                ) {
//                    Text(
//                        text = "Join Room",
//                        fontSize = 22.sp
//                    )
//                }
            }
        }
    }
}


@Preview
@Composable
fun PrevOptions() {
//    val context= LocalContext
//    GameEntryScreen(navController )
}