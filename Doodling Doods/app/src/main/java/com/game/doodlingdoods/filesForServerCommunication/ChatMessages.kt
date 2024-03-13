package com.game.doodlingdoods.filesForServerCommunication

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessages(
    var player: String,
    var room:String,
    var msgID: String,
    val msg: String,
    val msgColor: String,
    var visible: Boolean,
    var lifeCycle:Boolean = true,
    var chat:String=""
)

data class PlayerChats(
    var chats: ArrayList<ChatMessages>,
    var score: HashMap<String, Int>,
)

data class Chat(
    var chat: ChatMessages
)