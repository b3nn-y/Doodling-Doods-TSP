package com.game.doodlingdoods.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.game.doodlingdoods.R
import com.game.doodlingdoods.internetConnection.ConnectivityObserver
import com.game.doodlingdoods.internetConnection.NetworkConnectivityObserver
import com.game.doodlingdoods.viewmodels.MainActivityViewModel
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel

//This screen shows the options to join a existing room or create a new room for others to join.

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RoomsEntryScreen(
    navController: NavController,
    playerDetailsViewModel: PlayerDetailsViewModel
) {

    val connectivityObserver: ConnectivityObserver =
        NetworkConnectivityObserver(LocalContext.current)

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
        },
        connectivityObserver = connectivityObserver
    )

}

@Composable
private fun JoinGames(
    modifier: Modifier = Modifier,
    createRoomButtonClick: () -> Unit,
    joinRoomButtonClick: () -> Unit,
    connectivityObserver: ConnectivityObserver
) {
    val networkStatus by connectivityObserver.observe().collectAsState(
        initial = ConnectivityObserver.Status.Unavailable
    )
    val interactionSource = remember { MutableInteractionSource() }
    Log.i("network", networkStatus.toString())

    Surface(
        modifier.fillMaxSize(),
        shadowElevation = 40.dp
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "bg image",
            contentScale = ContentScale.FillBounds,
            modifier = modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
//            Image(painter = painterResource(id = R.drawable.editpencil),
//                contentDescription = "edit pencil",
//                modifier = Modifier
//                    .padding(start = 150.dp, top = 150.dp)
//                    .zIndex(10f)
//                    .align(Alignment.TopCenter)
//                    .clickable(
//                        interactionSource = interactionSource,
//                        indication = null
//                    ) {
//                        //Edit Profile actions
//                    }
//            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.avatar1),
                        contentDescription = "profile",
                        modifier = Modifier
                            .fillMaxHeight(0.2f)
                            .padding(top = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(painter = painterResource(id = R.drawable.join_room_button),
                        contentDescription = "join_room_button",
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                if (networkStatus.toString() == "Available") joinRoomButtonClick()
                            }
                    )
                }

                Image(painter = painterResource(id = R.drawable.create_room_button),
                    contentDescription = "create_room_button",
                    modifier = Modifier
                        .fillMaxWidth(0.5f)

                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            if (networkStatus.toString() == "Available") createRoomButtonClick()
                        }

                )

                Image(painter = painterResource(id = R.drawable.public_rooms_button),
                    contentDescription = "Public Rooms Button",
                    modifier = Modifier
                        .clickable {
                            //public room entry actions
                        }
                        .fillMaxWidth(0.5f)
                )

                if (networkStatus.toString() == "UnAvailable" || networkStatus.toString() == "Lost") {
                    CircularProgressIndicator(modifier = Modifier.width(50.dp))

                }

                if (networkStatus.toString() == "UnAvailable" || networkStatus.toString() == "Lost") {
                    CircularProgressIndicator(modifier = Modifier.width(50.dp))

                }

            }
        }
    }
}

//
//@Preview
//@Composable
//fun PrevOptions() {
//    val context = LocalContext
//    val navController = NavController(LocalContext.current)
//    RoomsEntryScreen(
//        navController = navController,
//        playerDetailsViewModel = PlayerDetailsViewModel()
//    )
//}