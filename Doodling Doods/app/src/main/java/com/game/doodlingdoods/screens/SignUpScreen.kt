package com.game.doodlingdoods.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.game.doodlingdoods.R
import com.game.doodlingdoods.ui.theme.signInFontFamily
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
        viewModel = viewModel
    )


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
    val interactionSource = remember { MutableInteractionSource() }



    Surface(
        modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "bg image",
            contentScale = ContentScale.FillBounds,
            modifier = modifier.fillMaxSize()
        )

        Column(
            modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Name",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = signInFontFamily,
                modifier = Modifier
                    .padding(start = 70.dp, 8.dp)
                    .align(Alignment.Start)
            )

            CustomOutlinedTextField(
                text = userName,
                onValueChange = { userName = it },
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Transparent),
                backgroundColor = Color.White
            )

            Text(
                text = "Email Id",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = signInFontFamily,
                modifier = Modifier
                    .padding(start = 70.dp, 8.dp)
                    .align(Alignment.Start)
            )


            CustomOutlinedTextField(
                text = mailId,
                onValueChange = { mailId = it },
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Transparent),
                backgroundColor = Color.White
            )

            Text(
                text = "Password",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = signInFontFamily,
                modifier = Modifier
                    .padding(start = 70.dp, 8.dp)
                    .align(Alignment.Start)
            )

            CustomOutlinedTextField(
                text = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .padding(0.dp)
                    .padding(8.dp)
                    .background(Color.Transparent),
                backgroundColor = Color.White
            )
            Image(
                painter =
                painterResource(id = R.drawable.sign_up_button),
                contentDescription = "Sign Up Button",
                modifier = Modifier
                    .size(200.dp)

                    .padding(0.dp)
                    .wrapContentHeight()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
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
                    }





            )


//            Card(
//                elevation = CardDefaults.cardElevation(
//                    defaultElevation = 10.dp
//                ),
//
//                modifier = modifier
//                    .width(200.dp)
//                    .padding(24.dp)
//                    .clickable {
//
//
//                        // creating account on Server
//                        if (viewModel.userInputFilter(
//                                userName = userName,
//                                mailId = mailId,
//                                password = password
//                            )
//                        ) {
//
//                            viewModel.signUpWithCredentials(userName, mailId, password)
//
//                        } else {
//                            Log.i("signup", "failed")
//
//                        }
//
//                    },)

//
//                ) {
//
//                Text(
//                    text = "Sign up",
//                    fontSize = 30.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier
//                        .align(Alignment.CenterHorizontally)
//                        .padding(8.dp)
//                )
//            }

            CircularProgressIndicator(
                modifier = Modifier.size(50.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevSignupScreen() {
    val navController = NavHostController(LocalContext.current)
    SignUpScreen(navController)
}