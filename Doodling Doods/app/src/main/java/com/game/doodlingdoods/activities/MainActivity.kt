package com.game.doodlingdoods.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.game.doodlingdoods.screens.HomeScreen
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
                            HomeScreen()
                        }
                    }
                }

                NavHost(navController = navController, graph =navGraph )
            }
        }
    }
}

