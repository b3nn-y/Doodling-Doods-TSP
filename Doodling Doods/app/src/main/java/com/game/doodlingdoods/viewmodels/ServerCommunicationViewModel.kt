package com.game.doodlingdoods.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playerManager.Player
import com.example.roomManager.Room
import com.game.doodlingdoods.data.RealtimeCommunicationClient
import com.game.doodlingdoods.drawingEssentials.Line
import com.game.doodlingdoods.drawingEssentials.LinesStorage
import com.game.doodlingdoods.filesForServerCommunication.Chat
import com.game.doodlingdoods.filesForServerCommunication.ChatMessages
import com.game.doodlingdoods.filesForServerCommunication.PlayerChats
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class ServerCommunicationViewModel @Inject constructor(
    private val client: RealtimeCommunicationClient
) : ViewModel() {
    var msgId = 0

    var guessedWords = HashMap<String, String>()
//    var chatMessages = ArrayList<ChatMessages>()

    private val _chatMessages = MutableStateFlow(ArrayList<ChatMessages>())
    val chatMessages: StateFlow<ArrayList<ChatMessages>> get() = _chatMessages

    var isGameStarted = false

    private var _currentPlayer = MutableStateFlow("Current Player")
    val currentPlayer: StateFlow<String>
        get() = _currentPlayer.asStateFlow()

    lateinit var room: Room
    private var _currentWord = MutableStateFlow("Nothing")
    val currentWord: StateFlow<String>
        get() = _currentWord.asStateFlow()


    var playersList = mutableStateListOf<Player>()

    var drawingCords = mutableStateListOf<Line>()
    private var _roomTime = MutableStateFlow("")

    private val _roomCreatedBy = MutableStateFlow("")
    val roomCreatedBy: StateFlow<String>
        get() = _roomCreatedBy.asStateFlow()

    val roomTime: StateFlow<String>
        get() = _roomTime.asStateFlow()

    private val _roundsPlayed = MutableStateFlow(0)
    val roundsPlayed: StateFlow<Int>
        get() = _roundsPlayed.asStateFlow()

    var messages = mutableListOf<String?>()

    private var _isConnectedWithServer = MutableStateFlow(false)
    private var _isDataSent = MutableStateFlow(false)

    var score = 5

    val isConnectedWithServer: StateFlow<Boolean>
        get() = _isConnectedWithServer.asStateFlow()

    val isDataSent = _isDataSent.asStateFlow()

    val playerScoreHashMap = hashMapOf<String, Int>().withDefault { 0 }


    lateinit var drawingOptions: ArrayList<String>
    var userChosenWord:String? = null

//    var _viewerPopup =MutableStateFlow(false)
//    val isPopedUp:StateFlow<Boolean>
//        get() = _viewerPopup.asStateFlow()
//

    private val _increasingNumber = MutableStateFlow(0)
    val increasingNumber: StateFlow<Int> = _increasingNumber


    var _chatListArr = MutableStateFlow(ArrayList<ChatMessages>())
    val chatListArr: StateFlow<ArrayList<ChatMessages>> get() = _chatListArr
    val addedMessages = HashMap<String, Int>()


    val state = client
        .getStateStream(path = "/connect")
        .onStart { _isConnecting.value = true }
        .onEach {
            _isConnecting.value = false
        }
        .catch { t ->
            _isConnecting.value = false
            _showConnectionError.value = t is ConnectException
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), String())

    private val _isConnecting = MutableStateFlow(false)
    val isConnecting = _isConnecting.asStateFlow()

    private val _showConnectionError = MutableStateFlow(false)
    val showConnectionError = _showConnectionError.asStateFlow()

    fun sendMessage(messageEvent: String) {
        viewModelScope.launch {
            client.sendAction(messageEvent)
            _isConnectedWithServer.value = true

        }
    }

    fun closeCommunication() {
        viewModelScope.launch {
            client.close()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            client.close()
        }
    }

    fun evaluateServerMessage(data: String): Room? {

        Log.i("chosenWord",userChosenWord.toString())
        println(data)
        try {
            val chatData = Gson().fromJson(data, PlayerChats::class.java)
            if (chatData.chats != null && chatData.score!=null){
                addMsg(chatData.chats)
                return null
            }

        }
        catch (e:Exception){
            println(e.message)
        }
        try {
            val roomData = Gson().fromJson(data, Room::class.java)
            if (roomData.name == null || roomData.pass == null || roomData.players != null || roomData.createdBy != null) {
                room = roomData
                playersList = mutableStateListOf()
                roomData.players.forEach {
                    playersList.add(it)
                }
                isGameStarted = roomData.gameStarted
                _currentPlayer.value = roomData.currentPlayer.name
                _chatMessages.value = roomData.messages

                Log.i("ServerData", roomData.createdBy.name)

                _roomCreatedBy.value = roomData.createdBy.name

                //round data
                _roundsPlayed.value = roomData.numberOfRoundsOver


                // for popup options
                drawingOptions = room.wordList


                playerScoreHashMap.clear()
                room.messages.forEach {
                    val currentPlayerScore = playerScoreHashMap.getOrPut(it.player) { 0 }
                    if (it.msgColor == "green") {
                        val updatedScore = currentPlayerScore + 5
                        playerScoreHashMap[it.player] = updatedScore
//                        Log.i("hashmap", playerScoreHashMap.toString())
                    } else {
//                        val currentPlayerScore = playerScoreHashMap.getOrPut(it.player) { 0 }
                        val updatedScore = currentPlayerScore
                        playerScoreHashMap[it.player] = updatedScore

                    }
                    Log.i("hashmap", playerScoreHashMap.toString())

                }

                Log.i("Room", "Updated")

                Log.i("hashmap", playerScoreHashMap.toString())
                //current word
                if (roomData.currentWordToGuess != null) {
                    _currentWord.value = roomData.currentWordToGuess
                } else {
                    _currentWord.value = "Nothing"
                }

                // timer
                _roomTime.value = formatSecondsToTime(roomData.timer)

                CoroutineScope(Dispatchers.Default + SupervisorJob()).launch {
                    try {
                        if (roomData.cords == "") {
                            drawingCords.clear()
                        } else {
                            drawingCords = (Gson().fromJson(
                                roomData.cords,
                                LinesStorage::class.java
                            ).lines).toMutableStateList()
                        }
                        Log.i("Lines", drawingCords.size.toString())
                        println(drawingCords + "\nI got some lines in viewmodel ${drawingCords.size}")
                    } catch (e: Exception) {
                        println(e.message)
                    }
                }

                Log.i("roomdata", roomData.toString())
                return roomData
            }


        } catch (e: Exception) {
//            println(e.message)
        }
        return null
    }

    fun sendRoomUpdate() {

        sendMessage(Gson().toJson(room))
        println("Sent data")
    }

    fun sendWord(word: String) {
        room.currentWordToGuess = word
        sendRoomUpdate()
    }

    fun formatSecondsToTime(seconds: Int): String {
//        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val remainingSeconds = seconds % 60

        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

    fun createMaskedWord(wordToGuess: String): String {
        var temp = ""
        for (i in wordToGuess.indices) {
            temp += if (i % 2 == 0) {
                wordToGuess[i]
            } else {
                "_"
            }
        }
        return temp
    }


    fun addMsg(chatData: ArrayList<ChatMessages>) {
        for (chat in chatData){
            if (!(addedMessages.containsKey(chat.msgID))){
                addedMessages[chat.msgID] = _chatListArr.value.size
                chatModerator(chat)
            }
        }

    }

    fun chatModerator(chatMessages: ChatMessages): Job {
        return viewModelScope.launch {
            val updatedList = ArrayList(_chatListArr.value)
            updatedList.add(chatMessages)
            _chatListArr.value = updatedList
            _increasingNumber.value = _increasingNumber.value.plus(1)
            delay(20)
            val updatedList2 = ArrayList(_chatListArr.value)
            updatedList2[addedMessages[chatMessages.msgID]!!].visible = true
            _chatListArr.value = updatedList2
//            println(updatedList2)
            _increasingNumber.value = _increasingNumber.value.plus(1)
            var chatTimer = 5
            while (chatTimer >= 0) {
                delay(1000)
                chatTimer--
            }

            val updatedList3 = ArrayList(_chatListArr.value)
            updatedList3[addedMessages[chatMessages.msgID]!!].visible = false
            _chatListArr.value = updatedList3
            _increasingNumber.value = _increasingNumber.value.plus(1)
            delay(2000)
            val updatedList4 = ArrayList(_chatListArr.value)
            updatedList4[addedMessages[chatMessages.msgID]!!].lifeCycle = false
            _chatListArr.value = updatedList4
            _increasingNumber.value = _increasingNumber.value.plus(1)
        }
    }

    fun sendChat(chatMessages: ChatMessages){
        println(Gson().toJson(Chat(chatMessages)))
        sendMessage(Gson().toJson(Chat(chatMessages)))
    }




}