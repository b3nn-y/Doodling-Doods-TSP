package com.game.doodlingdoods.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.game.doodlingdoods.R
import com.game.doodlingdoods.internetConnection.ConnectivityObserver
import com.game.doodlingdoods.internetConnection.NetworkConnectivityObserver
import com.game.doodlingdoods.screens.utils.HintPopup
import com.game.doodlingdoods.ui.theme.GameBlue
import com.game.doodlingdoods.viewmodels.MainActivityViewModel
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel
import com.game.doodlingdoods.viewmodels.RoomsEntryViewModel

//This screen shows the options to join a existing room or create a new room for others to join.

@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@Composable
fun RoomsEntryScreen(
    navController: NavController,
    playerDetailsViewModel: PlayerDetailsViewModel,
    mainActivityViewModel: MainActivityViewModel,
) {

    val roomsEntryViewModel = viewModel<RoomsEntryViewModel>()
    Log.i("popup321", roomsEntryViewModel._isHintActive.value.toString())
    Log.i("Server", roomsEntryViewModel.pingServer().toString())
    val hintActive by roomsEntryViewModel.isHintActive.collectAsState()

    if (hintActive) {
        HintPopup(
            closeButton = {
                roomsEntryViewModel._isHintActive.value = false
            },
            visible = true
        )
    } else {
        // it's checking whether the user name comes from db or guest
        if (mainActivityViewModel.getCurrentUserName() != null) {
            playerDetailsViewModel.playerName =
                mainActivityViewModel.getCurrentUserName().toString()
        }


        Log.i("PlayerName", playerDetailsViewModel.playerName)

        val connectivityObserver: ConnectivityObserver =
            NetworkConnectivityObserver(LocalContext.current)

        JoinGames(
            createRoomButtonClick = {
                playerDetailsViewModel.clickAudio.start()

                playerDetailsViewModel.joinType = "create"
                navController.navigate("CreateRoom")
                println("create room btn")


            },

            joinRoomButtonClick = {
                playerDetailsViewModel.clickAudio.start()

                playerDetailsViewModel.joinType = "join"
                navController.navigate("JoinRoom")
                println("Join room btn")
            },

            leaderBoardButton = {
                playerDetailsViewModel.clickAudio.start()

                navController.navigate("DashBoardScreen")
            },

            connectivityObserver = connectivityObserver,
            playerName = playerDetailsViewModel.playerName,
            roomsEntryViewModel = roomsEntryViewModel,
            playerViewModel = playerDetailsViewModel
        )
    }


}

@Composable
private fun JoinGames(
    modifier: Modifier = Modifier,
    createRoomButtonClick: () -> Unit,
    joinRoomButtonClick: () -> Unit,
    leaderBoardButton: () -> Unit,
    connectivityObserver: ConnectivityObserver,
    playerName: String,
    roomsEntryViewModel: RoomsEntryViewModel,
    playerViewModel: PlayerDetailsViewModel
) {
    val networkStatus by connectivityObserver.observe().collectAsState(
        initial = ConnectivityObserver.Status.Unavailable
    )
    val interactionSource = remember { MutableInteractionSource() }
    Log.i("network", networkStatus.toString())

    val context = LocalContext.current
    var counter by rememberSaveable { mutableIntStateOf(0) }

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
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxSize()
            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.leaderboard_icon),
//                    contentDescription = "Stats",
//                    modifier
//                        .padding(8.dp)
//                        .size(45.dp)
//                        .clickable {
//                            leaderBoardButton()
//                        }
//                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = playerName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier
                        .padding(bottom = 4.dp),
                    color = Color.White
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "backward",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(8.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                counter = if (counter == 0) 9 else counter - 1
                            }
                    )


                    val profilePic = when (counter) {
                        0 -> {
                            playerViewModel.profilePic = 0
                            R.drawable.avatar_dp_1
                        }

                        1 -> {
                            playerViewModel.profilePic = 1
                            R.drawable.avatar_dp_2
                        }

                        2 -> {
                            playerViewModel.profilePic = 2
                            R.drawable.avatar_dp_3
                        }

                        3 -> {
                            playerViewModel.profilePic = 3
                            R.drawable.avatar_dp_4
                        }

                        4 -> {
                            playerViewModel.profilePic = 4
                            R.drawable.avatar_dp_5
                        }

                        5 -> {
                            playerViewModel.profilePic = 5
                            R.drawable.avatar_dp_6
                        }

                        6 -> {
                            playerViewModel.profilePic = 6
                            R.drawable.avatar_dp_7
                        }

                        7 -> {
                            playerViewModel.profilePic = 7
                            R.drawable.avatar_dp_8
                        }

                        8 -> {
                            playerViewModel.profilePic = 8
                            R.drawable.avatar_dp_9
                        }

                        9 -> {
                            playerViewModel.profilePic = 9
                            R.drawable.avatar_dp_10
                        }

                        else -> R.drawable.profile
                    }



                    Image(
                        painter = painterResource(id = profilePic),
                        contentDescription = "profile",
                        modifier = Modifier
                            .fillMaxHeight(0.2f)
                            .padding(top = 16.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.arrow_forward),
                        contentDescription = "Forward",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(8.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                counter = (counter + 1) % 10
                            }
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
                                if (networkStatus.toString() == "Available" && roomsEntryViewModel.pingServer()) {

                                    joinRoomButtonClick()
                                } else {
                                    Toast
                                        .makeText(
                                            context,
                                            "You are not connected with server",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
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
                            if (networkStatus.toString() == "Available" && roomsEntryViewModel.pingServer()) {
                                createRoomButtonClick()
                            } else {
                                Toast
                                    .makeText(
                                        context,
                                        "You are not connected with server",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                        }

                )

                Image(painter = painterResource(id = R.drawable.public_rooms_button),
                    contentDescription = "Public Rooms Button",
                    modifier = Modifier
                        .clickable {
                            Toast
                                .makeText(context, "Coming Soon!!", Toast.LENGTH_SHORT)
                                .show()
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


@Preview(showSystemUi = true)
@Composable
fun PrevOptions() {
    val context = LocalContext
    val navController = NavController(LocalContext.current)
    val connectivityObserver: ConnectivityObserver =
        NetworkConnectivityObserver(LocalContext.current)
//    RoomsEntryScreen(
//        navController = navController,
//        playerDetailsViewModel = PlayerDetailsViewModel,
//        mainActivityViewModel = MainActivityViewModel(context.current)
//    )
    JoinGames(
        createRoomButtonClick = { /*TODO*/ },
        joinRoomButtonClick = { /*TODO*/ },
        leaderBoardButton = {},
        connectivityObserver = connectivityObserver,
        playerName = "Player name",
        roomsEntryViewModel = RoomsEntryViewModel(),
        playerViewModel = PlayerDetailsViewModel
    )
}