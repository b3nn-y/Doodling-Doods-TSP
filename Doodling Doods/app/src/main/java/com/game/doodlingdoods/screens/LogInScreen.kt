package com.game.doodlingdoods.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.game.doodlingdoods.R
import com.game.doodlingdoods.viewmodels.LoginScreenViewModel

//This screen is shown if the user wants to log in with their previous account
@Composable
fun LoginScreen(navController: NavHostController) {
    val viewmodel = viewModel(LoginScreenViewModel::class.java)
    LoginForms(navController = navController, viewmodel = viewmodel)

}

@Composable
private fun LoginForms(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewmodel: LoginScreenViewModel
) {
    val isSignInSuccess by viewmodel.isSignInSuccess.collectAsState()
    if (isSignInSuccess) navController.navigate("RoomsEntry")

    var mailId by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    Surface(

        modifier.fillMaxSize()

    ) {

        Column(
            modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            //email text field
            OutlinedTextField(
                value = mailId,
                onValueChange = { mailId = it },
                label = { Text(text = "E-mail") },
                singleLine = true,
                modifier = Modifier.padding(8.dp)
            )

            //password text field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") },
                singleLine = true,
                modifier = Modifier.padding(8.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

            )

            //For google authentication

//            Card(
//                elevation = CardDefaults.cardElevation(
//                    defaultElevation = 20.dp
//                ),
//                modifier = modifier.padding(8.dp)
//
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.google_icon),
//                    contentDescription = "google icon",
//                    modifier.size(40.dp)
//                        .padding(4.dp)
//
//                )
//            }


            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),

                modifier = modifier
                    .width(200.dp)
                    .padding(24.dp)
                    .clickable {
                        if (viewmodel.userInputFilter(mailId = mailId, password = password)) {
                            viewmodel.signInWithCredentials(mailId, password)

                        }


                    },


                ) {

                Text(
                    text = "Login",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp)
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun PrevLoginScreen() {
//    LoginForms()
//}