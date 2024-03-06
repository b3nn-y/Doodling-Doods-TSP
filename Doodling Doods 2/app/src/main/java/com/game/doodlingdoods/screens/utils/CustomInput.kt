package com.game.doodlingdoods.screens.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    placeholder: String
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
                textStyle = TextStyle.Default.copy(
                    fontSize = 23.sp,
                    color = Color.Black
                ),
                singleLine = true,


                placeholder = {
                    Text(
                        text = placeholder,
                        color = Color.Gray
                    )
                }


            )
        }
    }
}

@Composable
fun CustomPasswordField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    placeholder: String
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
                textStyle = TextStyle.Default.copy(
                    fontSize = 23.sp,
                    color = Color.Black
                ),
                placeholder = {
                    Text(
                        text = placeholder,
                        color = Color.Gray
                    )
                }

            )
        }
    }
}
