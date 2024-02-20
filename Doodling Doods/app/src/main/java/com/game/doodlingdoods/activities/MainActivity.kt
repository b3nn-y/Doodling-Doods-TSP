package com.game.doodlingdoods.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.game.doodlingdoods.screens.AccountSetup
import com.game.doodlingdoods.screens.CreateRoomScreen
import com.game.doodlingdoods.screens.GameScreen
import com.game.doodlingdoods.screens.RoomsEntryScreen
import com.game.doodlingdoods.screens.LobbyAdminScreen
import com.game.doodlingdoods.screens.HomeScreen
import com.game.doodlingdoods.screens.JoinRoomScreen
import com.game.doodlingdoods.screens.LobbyJoinerScreen
import com.game.doodlingdoods.screens.LoginScreen
import com.game.doodlingdoods.screens.SignUpScreen
import com.game.doodlingdoods.ui.theme.DoodlingDoodsTheme
import com.game.doodlingdoods.viewmodels.SignUpScreenViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("RememberReturnType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoodlingDoodsTheme {
                val navController = rememberNavController()
                val signUpScreenViewModel = viewModel<SignUpScreenViewModel>()
                val navGraph = remember(navController) {

                    navController.createGraph(startDestination = "HomeScreen") {
                        composable("HomeScreen") {
                            HomeScreen(navController = navController)
                        }

                        composable("AccountSetup") {
                            AccountSetup(navController = navController)
                        }

                        composable("LoginScreen") {
                            LoginScreen(navController = navController)
                        }

                        composable("SignUpScreen") {

                            SignUpScreen(
                                navController = navController,
                                signUpScreenViewModel
                            )
                        }
                        composable("AccountSetup"){
                            AccountSetup(navController = navController)
                        }

                        composable("RoomsEntry"){
                            RoomsEntryScreen(navController = navController)
                        }

                        composable("JoinRoom"){
                            JoinRoomScreen(navController = navController)
                        }

                        composable("CreateRoom"){
                            CreateRoomScreen(navController = navController)
                        }
                        composable("LobbyAdminScreen"){
                            LobbyAdminScreen(navController=navController)
                        }
                        composable("GameScreen"){
                            GameScreen(navController =navController)
                        }
                        composable("LobbyJoinerScreen"){
                           LobbyJoinerScreen(navController =navController)
                        }

                    }

                }

                NavHost(navController = navController, graph = navGraph)
            }
        }
    }
}

