package com.game.doodlingdoods.screens.utils

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.game.doodlingdoods.R
import com.game.doodlingdoods.filesForServerCommunication.ChatMessages
import com.game.doodlingdoods.ui.theme.Chat
import com.game.doodlingdoods.ui.theme.ChatBlue
import com.game.doodlingdoods.ui.theme.ChatPerson
import com.game.doodlingdoods.ui.theme.DarkGreen
import com.game.doodlingdoods.ui.theme.GameBlue
import com.game.doodlingdoods.ui.theme.ov_soge_bold
import com.game.doodlingdoods.viewmodels.PlayerDetailsViewModel
import com.game.doodlingdoods.viewmodels.ServerCommunicationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@Composable
fun ChatBar(
    serverViewModel: ServerCommunicationViewModel,
    player: PlayerDetailsViewModel,
    modifier: Modifier = Modifier,


    ) {

    var message by rememberSaveable {
        mutableStateOf("")
    }

//    val chatMessages by serverViewModel.chatMessages.collectAsState()
    val chatList = serverViewModel.chatListArr.value

    Column {
        Box (modifier =
        Modifier
            .fillMaxWidth()
            .height(100.dp)
        ) {
            LazyColumn() {
                items(chatList.asReversed()) {
                    MessageItem(message = it, visible = it.visible)
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
                            (message.lowercase().contains(serverViewModel.currentWord.value.lowercase()) && serverViewModel.currentPlayer.value == player.playerName) -> {
                                message = ""
                            }
                            (serverViewModel.currentWord.value.lowercase().trim() == message.lowercase().trim()) -> {
                                CoroutineScope(Dispatchers.IO).launch {
                                    val chat = (ChatMessages(player.playerName, player.roomName,"$player${serverViewModel.msgId++}","guessed it","green",false))
                                    serverViewModel.sendChat(chat)
                                    message = ""
                                }
                            }
                            else -> {
                                CoroutineScope(Dispatchers.IO).launch {
                                    val chat = (ChatMessages(player.playerName,player.roomName, "$player${serverViewModel.msgId++}",message,"black", false))

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
@Composable
fun MessageItem(message: ChatMessages, visible: Boolean) {
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = if (visible) tween(durationMillis = 700) else tween(durationMillis = 800),
        label = "" // Adjust duration as needed
    )
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = if (visible) tween(durationMillis = 300) else tween(durationMillis = 2000),
        label = "" // Adjust duration as needed
    )


    Box(
        modifier = Modifier
            .alpha(alpha)
            .scale(scale)
            .padding(8.dp)
    ) {
        MessageCard(name = message.player, msg = message.msg, color = message.msgColor)
    }
}


@Composable
fun MessageCard(name: String, msg: String, color: String) {
    Row (modifier = Modifier
        .background(shape = CircleShape, color = ChatBlue)
        .padding(vertical = 16.dp, horizontal = 10.dp)

    ){
        Box(
            modifier = Modifier
                .padding(start = 15.dp,end = 9.dp)


        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .align(Alignment.Center)
                    .clip(shape = CircleShape)
                    .border(2.dp, Color.White, CircleShape)

            )
        }

        Box(modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterVertically)
        ){
            Column {
                Text(text = name,
                    textAlign = TextAlign.Left,
                    fontFamily = ov_soge_bold,
                    fontSize = 20.sp,
                    color = ChatPerson
                )
                Text(text = msg,
                    textAlign = TextAlign.Left,
                    fontFamily = ov_soge_bold,
                    fontSize = 16.sp,
                    color = if (color == "black") Chat else DarkGreen
                )
            }
        }
    }
}

@Preview
@Composable
fun Msg(){
    MessageCard(name = "Ben", msg = "Hye whats up lets hook up a", "black")
}
