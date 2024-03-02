package com.game.doodlingdoods.screens.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.game.doodlingdoods.R
import com.game.doodlingdoods.ui.theme.GameBlue

@Composable
fun ChatBar(
    modifier: Modifier = Modifier,


    ) {
    var message by rememberSaveable {
        mutableStateOf("")
    }
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CustomOutlinedTextField(
            text = message,
            onValueChange = { message = it },
            modifier = Modifier
                .weight(0.9f)

                .padding(0.dp)
                .padding(bottom = 8.dp, end = 4.dp, start = 4.dp)
        )

        IconButton(modifier = Modifier
            .weight(0.2f)
            .size(80.dp)

            .fillMaxSize(),
            onClick = { }) {
            Icon(
                painter = painterResource(id = R.drawable.send),
                "contentDescription",
                tint = GameBlue
            )
        }
    }
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
        modifier = modifier
            .fillMaxWidth(0.75f),

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
                textStyle = TextStyle.Default.copy(fontSize = 20.sp),

                )
        }
    }
}

