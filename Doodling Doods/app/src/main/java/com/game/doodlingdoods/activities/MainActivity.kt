package com.game.doodlingdoods.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph

import com.game.doodlingdoods.screens.AccountSetup
import com.game.doodlingdoods.screens.CreateRoomScreen
import com.game.doodlingdoods.screens.DrawingScreen
import com.game.doodlingdoods.screens.GameScreen
import com.game.doodlingdoods.screens.RoomsEntryScreen
import com.game.doodlingdoods.screens.LobbyAdminScreen
import com.game.doodlingdoods.screens.HomeScreen
import com.game.doodlingdoods.screens.JoinRoomScreen
import com.game.doodlingdoods.screens.LobbyJoinerScreen
import com.game.doodlingdoods.screens.LoginScreen
import com.game.doodlingdoods.screens.SignUpScreen
import com.game.doodlingdoods.ui.theme.DoodlingDoodsTheme
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel
import com.game.doodlingdoods.viewmodels.ServerCommunicationViewModel
import com.game.doodlingdoods.viewmodels.SignUpScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//import androidx.hilt.navigation.compose.hiltViewModel
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("RememberReturnType", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            DoodlingDoodsTheme {
                val navController = rememberNavController()
                val playerDetailsViewModel: PlayerDetailsViewModel = viewModel()



                val navGraph = remember(navController) {

                    navController.createGraph(startDestination = "HomeScreen") {
                        composable("HomeScreen") {
                            HomeScreen(navController = navController)
                        }

                        composable("AccountSetup") {
                            AccountSetup(navController = navController, playerDetailsViewModel)
                        }

                        composable("LoginScreen") {
                            LoginScreen(navController = navController)
                        }

                        composable("SignUpScreen") {

                            SignUpScreen(navController = navController,)
                        }

                        composable("RoomsEntry") {
                            RoomsEntryScreen(navController = navController, playerDetailsViewModel)
                        }

                        composable("JoinRoom") {
                            JoinRoomScreen(navController = navController, playerDetailsViewModel)
                        }

                        composable("CreateRoom") {
                            CreateRoomScreen(navController = navController, playerDetailsViewModel)
                        }
                        composable("LobbyAdminScreen") {
                            LobbyAdminScreen(navController = navController, playerDetailsViewModel)
                        }
                        composable("GameScreen") {
                            GameScreen(navController = navController, playerDetailsViewModel)
                        }
                        composable("LobbyJoinerScreen") {
                            LobbyJoinerScreen(navController = navController, playerDetailsViewModel)
                        }
                        composable("DrawingScreen") {
                            DrawingScreen(navController = navController, playerDetailsViewModel)
                        }

                    }

                }

                NavHost(navController = navController, graph = navGraph)
            }
        }
    }
}

