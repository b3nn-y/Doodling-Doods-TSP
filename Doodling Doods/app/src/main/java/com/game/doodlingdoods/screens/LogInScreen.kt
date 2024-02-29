package com.game.doodlingdoods.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.game.doodlingdoods.R
import com.game.doodlingdoods.ui.theme.signInFontFamily
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

    Surface{

        Image(painter = painterResource(id = R.drawable.background),
            contentDescription ="bg image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds)

        Column(
            modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val doodle by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.doodle2))
            val interactionSource = remember { MutableInteractionSource() }

            LottieAnimation(composition = doodle, iterations = LottieConstants.IterateForever ,modifier = Modifier.size(250.dp))

            Column(modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.Center) {
                Text(text = "Log In",
                    fontFamily = signInFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 40.sp)


            }

            Text(text = "User Id",
                color = Color.White,
                fontFamily = signInFontFamily,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(start = 56.dp,8.dp)
                    .align(Alignment.Start))

            //email text field
            CustomOutlinedTextField(
                text = mailId,
                onValueChange = { mailId = it },
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Transparent),
                backgroundColor = Color.White
            )

            Text(text = "Password",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = signInFontFamily,
                modifier = Modifier
                    .padding(start = 56.dp,8.dp)
                    .align(Alignment.Start))

            //password text field
            CustomOutlinedTextField(
                text = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Transparent),
                backgroundColor = Color.White
            )

            Spacer(modifier = Modifier.height(40.dp))

            Image(painter = painterResource(id = R.drawable.login_button),
                contentDescription = "Sign In button",
                modifier = Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null
                ){
                    if (viewmodel.userInputFilter(mailId = mailId, password = password)) {
                        viewmodel.signInWithCredentials(mailId, password)
                    }
                }
                    .height(100.dp))
        }
    }
}

@Preview
@Composable
fun PrevLoginScreen() {
    LoginForms(navController = NavHostController(LocalContext.current),
        viewmodel = LoginScreenViewModel())
}

@Composable
fun CustomOutlinedTextField(
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

                )
        }
    }
}
