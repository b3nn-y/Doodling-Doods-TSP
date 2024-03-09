package com.game.doodlingdoods.filesForServerCommunication

data class ChatMessages(
    var player:String,
    var msgID: String,
    val msg: String,
    val msgSize: Int = 1,
    val msgColor: String,
)
