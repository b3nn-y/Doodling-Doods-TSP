package com.game.doodlingdoods.activities

//import com.game.doodlingdoods.test.DrawingScreen
import android.annotation.SuppressLint
import android.app.Activity
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.game.doodlingdoods.R
import com.game.doodlingdoods.factory.MainActivityViewModelFactory
import com.game.doodlingdoods.screens.AccountSetup
import com.game.doodlingdoods.screens.CreateRoomScreen
import com.game.doodlingdoods.screens.DashBoardScreen
import com.game.doodlingdoods.screens.DrawingScreen
import com.game.doodlingdoods.screens.EndGameScreen
import com.game.doodlingdoods.screens.GuestAccountScreen
import com.game.doodlingdoods.screens.HomeScreen
import com.game.doodlingdoods.screens.JoinRoomScreen
import com.game.doodlingdoods.screens.LeaderBoardScreen
import com.game.doodlingdoods.screens.LobbyAdminScreen
import com.game.doodlingdoods.screens.LobbyJoinerScreen
import com.game.doodlingdoods.screens.LoginScreen
import com.game.doodlingdoods.screens.RoomsEntryScreen
import com.game.doodlingdoods.screens.SignUpScreen
import com.game.doodlingdoods.screens.ViewerGameScreen
import com.game.doodlingdoods.ui.theme.DoodlingDoodsTheme
import com.game.doodlingdoods.viewmodels.MainActivityViewModel
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

//import androidx.hilt.navigation.compose.hiltViewModel
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var bgMusic: MediaPlayer

    @SuppressLint("RememberReturnType", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bgMusic = MediaPlayer.create(this, R.raw.doodsbackmusic)

        lifecycleScope.launch {
            bgMusic.isLooping=true
            bgMusic.start()
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContent {

            DoodlingDoodsTheme {
                val navController = rememberNavController()
                val playerDetailsViewModel: PlayerDetailsViewModel = PlayerDetailsViewModel

                playerDetailsViewModel.audioIntializer(LocalContext.current)

                val mainActivityViewModel:MainActivityViewModel = viewModel<MainActivityViewModel>(
                    factory = MainActivityViewModelFactory(LocalContext.current)
                )


                val navGraph = remember(navController) {

                    navController.createGraph(startDestination = "HomeScreen") {
                        composable("HomeScreen") {
                            if (mainActivityViewModel.convertEntityToData()){
                                RoomsEntryScreen(navController = navController, playerDetailsViewModel,mainActivityViewModel)
                            }else{
                                HomeScreen(navController = navController)

                            }
                            Log.i("RoomDb",mainActivityViewModel.convertEntityToData().toString())

                        }

                        composable("AccountSetup") {
                            AccountSetup(navController = navController, playerDetailsViewModel)
                        }

                        composable("LoginScreen") {
                            LoginScreen(navController = navController,mainActivityViewModel,playerDetailsViewModel)
                        }

                        composable("SignUpScreen") {
                            SignUpScreen(navController = navController,mainActivityViewModel)
                        }

                        composable("RoomsEntry") {
                            RoomsEntryScreen(navController = navController, playerDetailsViewModel,mainActivityViewModel)
                        }

                        composable("JoinRoom") {
                            JoinRoomScreen(navController = navController, playerDetailsViewModel)
                        }

                        composable("CreateRoom") {
                            CreateRoomScreen(navController = navController)
                        }
                        composable("LobbyAdminScreen") {
                            LobbyAdminScreen(navController = navController, playerDetailsViewModel)
                        }
                        composable("GameScreen") {
                            ViewerGameScreen(navController = navController, playerDetailsViewModel)
                        }
                        composable("LobbyJoinerScreen") {
                            LobbyJoinerScreen(navController = navController, playerDetailsViewModel)
                        }
                        composable("DrawingScreen") {
                            DrawingScreen(navController = navController, playerDetailsViewModel)
                        }
                        composable("GuestAccountScreen") {
                            GuestAccountScreen(navController = navController, playerDetailsViewModel)
                        }
                        composable("EndGameScreen"){
                            EndGameScreen(navController=navController,playerDetailsViewModel)
                        }
                        composable("LeaderBoardScreen"){
                            LeaderBoardScreen(navController=navController,playerDetailsViewModel)
                        }
                        composable("DashBoardScreen"){
                            DashBoardScreen(navController = navController)
                        }

                    }

                }

                NavHost(navController = navController, graph = navGraph)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (bgMusic.isPlaying) {
            bgMusic.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!bgMusic.isPlaying) {
            bgMusic.start()
        }
    }
}

