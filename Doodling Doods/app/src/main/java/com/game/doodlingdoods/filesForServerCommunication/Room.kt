package com.game.doodlingdoods.filesForServerCommunication

import com.example.playerManager.Player

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
    var gameOver:Boolean = false,
    var iosCords: ArrayList<IosCords>,
    var isWordChosen: Boolean = false,
    var wordType:String = "ZohoProducts",
)


data class IosCords(
    var points: ArrayList<Point>,
    var color: RGB,
    var lineWidth: Double
)
data class Point(
    var x: Double,
    var y: Double
)

data class RGB(
    var red: Double,
    var green: Double,
    var blue: Double
)