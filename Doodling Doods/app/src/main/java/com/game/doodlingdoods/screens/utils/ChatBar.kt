package com.game.doodlingdoods.screens.utils

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.game.doodlingdoods.R
import com.game.doodlingdoods.filesForServerCommunication.ChatMessages
import com.game.doodlingdoods.ui.theme.DarkGreen
import com.game.doodlingdoods.ui.theme.GameBlue
import com.game.doodlingdoods.ui.theme.ov_soge_bold
import com.game.doodlingdoods.viewmodels.ServerCommunicationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ChatBar(
    serverViewModel: ServerCommunicationViewModel,
    player: String,
    modifier: Modifier = Modifier,


    ) {

    var message by rememberSaveable {
        mutableStateOf("")
    }

    val chatMessages by serverViewModel.chatMessages.collectAsState()


    Column {
        Box (modifier =
        Modifier
            .fillMaxWidth()
            .height(100.dp)
        ) {
            LazyColumn() {
                items(chatMessages.asReversed()) {
                    val score = when (it.msgColor) {
                        "white" -> Color.White
                        "green" -> {


//                            val currentPlayerScore =
//                                serverViewModel.playerScoreHashMap.getOrPut(it.player) { 0 }
//                            val updatedScore = currentPlayerScore + 5
//                            serverViewModel.playerScoreHashMap[it.player] = updatedScore
//                            Log.i("hashmap", serverViewModel.playerScoreHashMap.toString())
                            DarkGreen
                        }

                        else -> Color.Black
                    }

                    Text(
                        text = if (it.player == player) "You: ${it.msg}" else "${it.player}: ${it.msg}",
                        fontFamily = ov_soge_bold,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        color = score
                    )
                }
            }
        }
            Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(modifier = Modifier
                .weight(0.2f)
                .size(50.dp)

                .fillMaxSize(),
                onClick = {

                }) {
                Icon(
                    painter = painterResource(id = R.drawable.chat),
                    "contentDescription",
                    tint = Color.Black

                    )
            }

            CustomOutlinedTextField(
                text = message,
                onValueChange = { message = it },
                modifier = Modifier
                    .weight(0.8f)

                    .padding(0.dp)
                    .padding(bottom = 8.dp, end = 4.dp, start = 4.dp)
            )

            IconButton(modifier = Modifier
                .weight(0.2f)
                .size(50.dp)

                .fillMaxSize(),
                onClick = {
                    if (message.isNotEmpty()){
                        when{
                            (message.lowercase().contains(serverViewModel.currentWord.value.lowercase()) && serverViewModel.currentPlayer.value == player) -> {
                                message = ""
                            }
                            (serverViewModel.currentWord.value.lowercase().trim() == message.lowercase().trim()) -> {
                                CoroutineScope(Dispatchers.IO).launch {
                                    serverViewModel.room.messages.add(ChatMessages(player, "$player${serverViewModel.msgId}","guessed it", 1,"green"))
                                    serverViewModel.sendRoomUpdate()
                                    message = ""
                                }
                            }
                            else -> {
                                CoroutineScope(Dispatchers.IO).launch {
                                    serverViewModel.room.messages.add(ChatMessages(player, "$player ${serverViewModel.msgId}",message, 1,"black"))
                                    serverViewModel.sendRoomUpdate()
                                    message = ""
                                }
                            }
                        }
                    }
//                    if (serverViewModel.currentWord.value.lowercase() == message){
//                        CoroutineScope(Dispatchers.IO).launch {
//                            serverViewModel.room.messages.add(ChatMessages(player, "$player${serverViewModel.msgId}",message, 1,"green"))
//                            serverViewModel.sendRoomUpdate()
//                        }
//                    }
//                    else{
//                        CoroutineScope(Dispatchers.IO).launch {
//                            serverViewModel.room.messages.add(ChatMessages(player, "$player ${serverViewModel.msgId}",message, 1,"black"))
//                            serverViewModel.sendRoomUpdate()
//                            message = ""
//                        }
//                    }
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.sendmessage),
                    "contentDescription",
                    tint = GameBlue
                )
            }
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
                textStyle = TextStyle.Default.copy(fontSize = 20.sp, color = Color.Black),
                placeholder = {
                    Text(
                        text = "Enter something",
                        color = Color.Gray
                    )
                }

                )
        }
    }
}

