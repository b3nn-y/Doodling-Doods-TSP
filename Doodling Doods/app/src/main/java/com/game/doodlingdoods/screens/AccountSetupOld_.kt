package com.game.doodlingdoods.screens
//
//
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.wrapContentSize
//import androidx.compose.material3.Card
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel
//
////This screen shows the option to either create a temp username and join a game, or to sign up or login with a email account to store their data
//
//@Composable
//fun AccountSetup(
//    navController: NavController,
//    playerDetailsViewModel: PlayerDetailsViewModel,
//    modifier: Modifier = Modifier
//) {
//    Box(
//        modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Column(
//            Modifier.fillMaxWidth(),
//            verticalArrangement = Arrangement.SpaceBetween,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Box(
//                modifier.weight(1f),
//                contentAlignment = Alignment.Center
//            ) {
//                GuestButton(navController = navController, playerDetailsViewModel = playerDetailsViewModel)
//            }
//            Box(
//                modifier.weight(1f),
//                contentAlignment = Alignment.Center
//            ) {
//                OptionsHandler(navController=navController)
//            }
//        }
//    }
//}
//
//
////for creating guest account
//@Composable
//private fun GuestButton(modifier: Modifier = Modifier, navController: NavController, playerDetailsViewModel: PlayerDetailsViewModel) {
//    var guestName by rememberSaveable {
//        mutableStateOf("")
//    }
//    Box(
//        modifier.fillMaxWidth()
//
//    ) {
//        Column(
//            modifier.fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = "Play as Guest User",
//                fontSize = 20.sp,
//                modifier = Modifier.padding(8.dp)
//            )
//
//
//            OutlinedTextField(
//                value = guestName,
//                onValueChange = { guestName = it },
//                modifier
//                    .padding(horizontal = 35.dp)
//                    .padding(16.dp)
//                    .fillMaxWidth(),
//                label = { Text(text = "Name") },
//                singleLine = true
//
//
//            )
//
//
//            Card(
//                modifier
//                    .width(200.dp)
//                    .padding(20.dp)
//
//            ) {
//                Text(
//                    text = "Play",
//                    fontSize = 30.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier
//                        .align(Alignment.CenterHorizontally)
//                        .padding(8.dp)
//                        .clickable {
//                            playerDetailsViewModel.playerName = guestName
//                            navController.navigate("RoomsEntry")
//                        }
//                )
//            }
//
//
//        }
//    }
//
//}
//
//// this function handles weather to launch login screen or sign up
//@Composable
//fun OptionsHandler(
//    navController: NavController,
//    modifier: Modifier = Modifier
//) {
//    Box(
//        modifier
//            .fillMaxSize()
//            .wrapContentSize(),
//        contentAlignment = Alignment.Center,
//
//        ) {
//        Column(
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            LoginButton(
//                LoginButtonClick = {
//                    navController.navigate("LoginScreen")
//                }
//            )
//            Text(
//                text = "Or",
//                modifier.padding(8.dp),
//                fontSize = 20.sp
//            )
//            SignUpButton(
//                SignUpButtonClick = {
//                    navController.navigate("SignUpScreen")
//                }
//            )
//        }
//    }
//}
//
//@Composable
//fun LoginButton(
//    modifier: Modifier = Modifier,
//    LoginButtonClick: () -> Unit
//) {
//    Card(
//        modifier
//            .fillMaxWidth()
//            .padding(horizontal = 48.dp, vertical = 8.dp)
//            .clickable {
//                LoginButtonClick()
//            },
//
//        ) {
//        Text(
//            text = "Login",
//            fontSize = 30.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//                .padding(8.dp)
//        )
//    }
//}
//
//@Composable
//fun SignUpButton(
//    modifier: Modifier = Modifier,
//    SignUpButtonClick: () -> Unit
//) {
//    Card(
//        modifier
//            .fillMaxWidth()
//            .padding(horizontal = 48.dp, vertical = 8.dp)
//            .clickable {
//                SignUpButtonClick()
//            },
//
//        ) {
//        Text(
//            text = "Sign Up",
//            fontSize = 30.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//                .padding(8.dp)
//        )
//    }
//}

//@Preview(showSystemUi = true)
//@Composable
//fun Prev() {
//    LoginButton(LoginButtonClick = {})
//    SignUpButton(SignUpButtonClick = {})
//    GuestButton()
//    AccountSetup()
//    OptionsHandler()
//}


