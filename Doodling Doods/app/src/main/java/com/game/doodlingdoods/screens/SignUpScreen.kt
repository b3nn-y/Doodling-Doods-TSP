package com.game.doodlingdoods.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.ProgressBar
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.game.doodlingdoods.viewmodels.SignUpScreenViewModel

//This screen is shown if the user wants to sign up.
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SignUpScreen(
    navController: NavHostController,

) {
    val viewModel = viewModel(SignUpScreenViewModel::class.java)


    SignUpForms(
        navController,
        viewModel = viewModel)



}


//Sign up forms for collecting email
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun SignUpForms(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SignUpScreenViewModel
) {
    val isSignUpSuccess by viewModel.isSignUpSuccess.collectAsState()

    if (isSignUpSuccess) navController.navigate("RoomsEntry")


    var userName by rememberSaveable {
        mutableStateOf("")
    }
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

            //name text field
            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text(text = "Name") },
                singleLine = true,
                modifier = Modifier.padding(8.dp)
            )

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



            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),

                modifier = modifier
                    .width(200.dp)
                    .padding(24.dp)
                    .clickable {


                        // creating account on Server
                        if (viewModel.userInputFilter(
                                userName = userName,
                                mailId = mailId,
                                password = password
                            )
                        ) {

                            viewModel.signUpWithCredentials(userName, mailId, password)

                        } else {
                            Log.i("signup", "failed")

                        }

                    },


                ) {

                Text(
                    text = "Sign up",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp)
                )
            }

            CircularProgressIndicator(
                modifier = Modifier.size(50.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevSignupScreen() {
    val navController =NavHostController(LocalContext.current)
    SignUpScreen(navController)
}