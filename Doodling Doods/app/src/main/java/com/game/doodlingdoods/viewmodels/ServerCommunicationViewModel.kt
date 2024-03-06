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
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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

    var isGameStarted = false

    var currentPlayer = ""

    lateinit var room: Room

    var playersList = mutableStateListOf<String>()

    var drawingCords = mutableStateListOf<Line>()


    var messages = mutableListOf<String?>()

    private var  _isConnectedWithServer =  MutableStateFlow(false)
    private var _isDataSent = MutableStateFlow(false)

    val isConnectedWithServer:StateFlow<Boolean>
        get() = _isConnectedWithServer.asStateFlow()

    val isDataSent= _isDataSent.asStateFlow()

    val state = client
        .getStateStream(path = "/connect")
        .onStart { _isConnecting.value = true }
        .onEach { _isConnecting.value = false
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
            _isConnectedWithServer.value=true

        }
    }

    fun closeCommunication(){
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

        println(data)
        try {
            val roomData = Gson().fromJson(data, Room::class.java)
            if (roomData.name == null || roomData.pass == null || roomData.players != null || roomData.createdBy != null ){
                room = roomData
                playersList = mutableStateListOf()
                roomData.players.forEach{
                    playersList.add(it.name)
                }
                isGameStarted = roomData.gameStarted
                currentPlayer = roomData.currentPlayer.name
                Log.i("Room", "Updated")
                CoroutineScope(Dispatchers.Default + SupervisorJob()).launch {
                    try {
                        if (roomData.cords == ""){
                            drawingCords.clear()
                        }
                        else{
                            drawingCords = (Gson().fromJson(roomData.cords, LinesStorage::class.java).lines).toMutableStateList()
                        }
                        Log.i("Lines", drawingCords.size.toString())
                        println(drawingCords + "\nI got some lines in viewmodel ${drawingCords.size}")
                    }
                    catch (e: Exception){
                        println(e.message)
                    }
                }
                return roomData
            }



        }
        catch (e:Exception){
//            println(e.message)
        }
        return null
    }

    fun sendRoomUpdate(){
        sendMessage(Gson().toJson(room))
        println("Sent data")
    }

    fun sendWord(word:String){
        room.currentWordToGuess = word
        sendRoomUpdate()
    }


}