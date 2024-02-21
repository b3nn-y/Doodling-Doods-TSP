package com.game.doodlingdoods.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.game.doodlingdoods.viewmodels.SignUpScreenViewModel

//This screen is shown if the user wants to sign up.
@Composable
fun SignUpScreen(
    navController: NavHostController,
    signUpScreenViewModel: SignUpScreenViewModel
) {
    SignUpForms(
        navController,
        signUpScreenViewModel = SignUpScreenViewModel())


}


//Sign up forms for collecting email
@Composable
private fun SignUpForms(
    navController:NavHostController,
    modifier: Modifier = Modifier,
    signUpScreenViewModel: SignUpScreenViewModel
) {

    val context = LocalContext.current
    // initializing view model
    val viewModel = viewModel(modelClass = SignUpScreenViewModel::class.java)

    var name by rememberSaveable {
        mutableStateOf("")
    }
    var email by rememberSaveable {
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
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Name") },
                singleLine = true,
                modifier = Modifier.padding(8.dp)
            )

            //email text field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
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
                        //Test

//                        try {
//                            signUpScreenViewModel.getRoomsFromApi()
//
//
//                        } catch (e: Exception) {
//                            Log.i("response",e.toString())
//                        }


                        // creating account on fire base
                        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {

                            viewModel.firebaseAuth
                                .createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { it ->
                                    if (it.isSuccessful) {

                                        //Adding user name and email to our db
                                        Log.i("firebase", "User created")
                                        viewModel.addUserDetailsToCloud(
                                            userEmail = email,
                                            name = name
                                        )

                                        navController.navigate("RoomsEntry")
                                    } else {
                                        Log.i("firebase", it.exception.toString())
                                    }

                                }
                        } else {
                            Toast
                                .makeText(context, "Check you credentials", Toast.LENGTH_SHORT)
                                .show()
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
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevSignupScreen() {
//    SignUpScreen()
}