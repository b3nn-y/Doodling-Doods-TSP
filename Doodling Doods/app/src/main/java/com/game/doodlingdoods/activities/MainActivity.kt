package com.game.doodlingdoods.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.game.doodlingdoods.screens.AccountSetup
import com.game.doodlingdoods.screens.HomeScreen
import com.game.doodlingdoods.screens.LoginScreen
import com.game.doodlingdoods.screens.SignUpScreen
import com.game.doodlingdoods.ui.theme.DoodlingDoodsTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("RememberReturnType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoodlingDoodsTheme {
                val navController= rememberNavController()

                val navGraph= remember(navController){

                    navController.createGraph(startDestination = "HomeScreen"){
                        composable("HomeScreen"){
                            HomeScreen(navController = navController)
                        }

                        composable("AccountSetup"){
                            AccountSetup(navController = navController)
                        }

                        composable("LoginScreen"){
                            LoginScreen(navController = navController)
                        }

                        composable("SignUpScreen"){
                            SignUpScreen(navController = navController)
                        }
                    }

                }

                NavHost(navController = navController, graph =navGraph )
            }
        }
    }
}

