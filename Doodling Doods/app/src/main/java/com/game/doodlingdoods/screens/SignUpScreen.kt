package com.game.doodlingdoods.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.game.doodlingdoods.R
import com.game.doodlingdoods.internetConnection.ConnectivityObserver
import com.game.doodlingdoods.internetConnection.NetworkConnectivityObserver
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

            CustomOutlinedTextField(
                text = userName,
                onValueChange = { userName = it },
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Transparent),
                backgroundColor = Color.White
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

            CustomOutlinedTextField(
                text = mailId,
                onValueChange = { mailId = it },
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Transparent),
                backgroundColor = Color.White
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

            CustomOutlinedPasswordField(
                text = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Transparent),
                backgroundColor = Color.White
            )

            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter =
                painterResource(id = R.drawable.sign_up_button),
                contentDescription = "Sign Up Button",
                modifier = Modifier
                    .size(200.dp)

                    .padding(0.dp)
                    .wrapContentHeight()
                    .clickable{
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
                    modifier = Modifier.size(50.dp)
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

@Composable
private fun CustomOutlinedTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White
) {
    Surface(
        shadowElevation = 20.dp,
        shape = RoundedCornerShape(50),
        modifier = modifier.fillMaxWidth(0.75f),
        color = backgroundColor
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            OutlinedTextField(
                shape = RoundedCornerShape(50),
                value = text,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = TextStyle.Default.copy(fontSize = 23.sp),
                singleLine = true,


            )
        }
    }
}

@Composable
private fun CustomOutlinedPasswordField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White
) {
    Surface(
        shadowElevation = 20.dp,
        shape = RoundedCornerShape(50),
        modifier = modifier.fillMaxWidth(0.75f),
        color = backgroundColor
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            OutlinedTextField(
                shape = RoundedCornerShape(50),
                value = text,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = TextStyle.Default.copy(fontSize = 23.sp),

            )
        }
    }
}
