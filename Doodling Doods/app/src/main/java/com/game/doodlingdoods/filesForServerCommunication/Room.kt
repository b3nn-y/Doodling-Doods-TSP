package com.example.roomManager

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import com.example.playerManager.Player
import com.game.doodlingdoods.drawingEssentials.Line
import com.game.doodlingdoods.filesForServerCommunication.ChatMessages

data class Room(
    var name:String,
    var pass:String,
    var players: ArrayList<Player>,
    var noOfPlayersInRoom: Int = 0,
    var noOfGuessedAnswersInCurrentRound: Int = 0,
    var createdBy: Player,
    var admin: Boolean,
    var maxPlayers: Int = 10,
    var cords:String  =  "",
    var visibility: Boolean,
    var currentPlayer: Player,
    var rounds: Int = 3,
    var currentWordToGuess:String,
    var gameStarted:Boolean = false,
    var gameMode:String = "Guess The Word",
    var wordList: ArrayList<String>,
    var guessedPlayers: ArrayList<String>,
    var timer: Int = 0,
    var messages: ArrayList<ChatMessages>,
    var numberOfRoundsOver:Int=0,
    var gameOver:Boolean = false
)
