package com.game.doodlingdoods.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.game.doodlingdoods.viewmodels.GuestScreenViewModel

@Composable
fun GuestScreen(navController: NavHostController){
    val viewModel = viewModel<GuestScreenViewModel>()
}
