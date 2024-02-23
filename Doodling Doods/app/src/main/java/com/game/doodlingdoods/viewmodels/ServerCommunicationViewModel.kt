package com.game.doodlingdoods.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playerManager.Player
import com.example.roomManager.Room
import com.game.doodlingdoods.data.RealtimeCommunicationClient
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
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

    private lateinit var room: Room


    var messages = mutableListOf<String?>()

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

    fun evaluateServerMessage(data: String){

        println(data)
        try {
            val roomData = Gson().fromJson(data, Room::class.java)
            if (roomData.name == null || roomData.pass == null || roomData.players != null || roomData.createdBy != null ){
                room = roomData
                Log.i("Room", "Updated")
            }
        }
        catch (e:Exception){
//            println(e.message)
        }
    }

    fun sendRoomUpdate(){
        sendMessage(Gson().toJson(room))
    }


}