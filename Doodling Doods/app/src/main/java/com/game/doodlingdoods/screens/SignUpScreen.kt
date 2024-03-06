package com.game.doodlingdoods.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.game.doodlingdoods.internetConnection.ConnectivityObserver
import com.game.doodlingdoods.internetConnection.NetworkConnectivityObserver
import com.game.doodlingdoods.screens.utils.CustomPasswordField
import com.game.doodlingdoods.screens.utils.CustomTextField
import com.game.doodlingdoods.ui.theme.signInFontFamily
import com.game.doodlingdoods.viewmodels.MainActivityViewModel
import com.game.doodlingdoods.viewmodels.SignUpScreenViewModel


//This screen is shown if the user wants to sign up.
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SignUpScreen(
    navController: NavHostController,
    mainActivityViewModel: MainActivityViewModel

) {

    val viewModel = viewModel<SignUpScreenViewModel>()

    val connectivityObserver: ConnectivityObserver =
        NetworkConnectivityObserver(LocalContext.current)

    SignUpForms(
        navController,
        viewModel = viewModel,
        connectivityObserver = connectivityObserver,
        mainActivityViewModel = mainActivityViewModel
    )


}


//Sign up forms for collecting email
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun SignUpForms(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SignUpScreenViewModel,
    connectivityObserver: ConnectivityObserver,
    mainActivityViewModel: MainActivityViewModel
) {
    var userName by rememberSaveable {
        mutableStateOf("")
    }
    var mailId by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    val networkStatus by connectivityObserver.observe().collectAsState(
        initial = ConnectivityObserver.Status.Unavailable
    )
//    val interactionSource = remember { MutableInteractionSource() }



    Log.i("Network", networkStatus.toString())

    val isSignUpSuccess by viewModel.isSignUpSuccess.collectAsState()

    if (isSignUpSuccess) {

        mainActivityViewModel.makeAsLoggedUser(userName,mailId)

        navController.navigate("RoomsEntry")

    }




//    val status by viewModel.connectivityObserver.observe().collectAsState(
//        initial = ConnectivityObserver.Status.Unavailable
//    )


    Surface(
        modifier.fillMaxSize()

    ) {
        val context = LocalContext.current
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


            //name text field
            Text(
                text = "Name",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = signInFontFamily,
                modifier = Modifier
                    .padding(start = 70.dp, 8.dp)
                    .align(Alignment.Start)
            )



            CustomTextField(
                text = userName,
                onValueChange = { userName = it },
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Transparent),
                backgroundColor = Color.White,
                placeholder = "UserName"
            )

            //email text field
            Text(
                text = "Email Id",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = signInFontFamily,
                modifier = Modifier
                    .padding(start = 70.dp, 8.dp)
                    .align(Alignment.Start)
            )

            CustomTextField(
                text = mailId,
                onValueChange = { mailId = it },
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Transparent),
                backgroundColor = Color.White,
                placeholder = "Mail Id"
            )

            //password text field
            Text(
                text = "Password",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = signInFontFamily,
                modifier = Modifier
                    .padding(start = 70.dp, 8.dp)
                    .align(Alignment.Start)
            )

            CustomPasswordField(
                text = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Transparent),
                backgroundColor = Color.White,
                placeholder = "Password"
            )



            Image(
                painter =
                painterResource(id = R.drawable.sign_up_button),
                contentDescription = "Sign Up Button",
                modifier = Modifier
                    .size(200.dp)
                    .padding(0.dp)
                    .wrapContentHeight()
                    .clickable {
                        if (networkStatus.toString() == "Available") {
                            // creating account on Server
                            if (viewModel.userInputFilter(
                                    userName = userName,
                                    mailId = mailId,
                                    password = password
                                )
                            ) {

                                viewModel.signUpWithCredentials(userName, mailId, password)

                            } else {
                                Toast
                                    .makeText(
                                        context,
                                        "Check Your name or email and password contains 8 characters",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()

                                Log.i("signup", "failed")

                            }
                        } else {
                            Log.i("Network", networkStatus.toString())
                        }
                    }


            )


            if (networkStatus.toString() == "Unavailable"
                || networkStatus.toString() == "Lost"
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(8.dp),

                )
            }

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevSignupScreen() {
    val navController = NavHostController(LocalContext.current)
    SignUpScreen(navController, MainActivityViewModel(LocalContext.current))
}

