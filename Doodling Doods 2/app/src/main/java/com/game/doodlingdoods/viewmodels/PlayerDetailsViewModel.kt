package com.game.doodlingdoods.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playerManager.Player
import com.game.doodlingdoods.GameApi.KtorServerApi
import com.game.doodlingdoods.filesForServerCommunication.RoomAvailability
import kotlinx.coroutines.launch
import retrofit2.Response

class PlayerDetailsViewModel: ViewModel() {
    var playerName = ""
    var joinType = ""
    var roomName = ""
    var roomPass = ""
    var admin = false
    var roomAvailability = mutableStateOf("")

    val currentPlayer = "Abc"
    val guessWord = "Y_u_ W_r_"
    val randomWord = "Your word" //for drawer screen

    lateinit var serverCommunicationViewModel: ServerCommunicationViewModel

    fun initializeServerViewModel(communicationViewModel: ServerCommunicationViewModel){
        serverCommunicationViewModel = communicationViewModel
    }

    fun checkRoomAvailability(){
        viewModelScope.launch {

            val response = pushPost(roomName)

            if (response.isSuccessful){
                Log.i("Api", response.body().toString())
                val data = response.body()
                if (data?.roomAvailable == true){
                    roomAvailability.value = ""
                    if (data.roomPass != roomPass){
                        roomAvailability.value = "wrong pass"
                    }
                    else{
                        roomAvailability.value = "verified"
                    }

                }
                else{
                    roomAvailability.value = "no room"
                }


            }
            else{
                Log.i("Api", response.errorBody().toString())
                Log.i("Api", response.code().toString())
            }
        }

    }

    suspend fun pushPost(post: String): Response<RoomAvailability>{
        return KtorServerApi.api.checkRoom(post)
    }

    fun getPlayerData():Player{

        return Player(name = playerName, joinType = joinType, roomName = roomName, roomPass = roomPass, admin= admin)
    }
}