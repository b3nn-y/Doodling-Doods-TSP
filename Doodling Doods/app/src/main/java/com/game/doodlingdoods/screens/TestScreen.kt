package com.game.doodlingdoods.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.game.doodlingdoods.screens.AccountSetup
import com.game.doodlingdoods.screens.HomeScreen
import com.game.doodlingdoods.screens.LoginScreen
import com.game.doodlingdoods.screens.SignUpScreen
import com.game.doodlingdoods.viewmodels.SignUpScreenViewModel

//@Composable
//fun AppPreview() {
//    val navController = rememberNavController()
//    val signUpScreenViewModel = viewModel<SignUpScreenViewModel>()
//    val navGraph = remember(navController) {
//
//        navController.createGraph(startDestination = "HomeScreen") {
//            composable("HomeScreen") {
//                HomeScreen(navController = navController)
//            }
//
//            composable("AccountSetup") {
//                AccountSetup(navController = navController)
//            }
//
//            composable("LoginScreen") {
//                LoginScreen(navController = navController)
//            }
//
//            composable("SignUpScreen") {
//
//                SignUpScreen(
//                    navController = navController,
//                    signUpScreenViewModel
//                )
//            }
//        }
//
//    }
//}



