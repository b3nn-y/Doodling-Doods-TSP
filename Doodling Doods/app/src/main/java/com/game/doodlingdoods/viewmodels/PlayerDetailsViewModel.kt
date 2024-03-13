package com.game.doodlingdoods.viewmodels

import android.content.Context
import android.media.MediaPlayer
import android.provider.MediaStore.Audio.Media
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playerManager.Player
import com.game.doodlingdoods.GameApi.KtorServerApi
import com.game.doodlingdoods.R
import com.game.doodlingdoods.filesForServerCommunication.RoomAvailability
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response

object PlayerDetailsViewModel {
    var playerName = ""
    var joinType = ""
    var roomName = ""
    var roomPass = ""
    var profilePic = 0
    var admin = false
    var roomAvailability = mutableStateOf("")
    //Color Picker
    private val _currentColor = MutableStateFlow<ColorEnvelope?>(null)
    private val _barColor = MutableStateFlow<Color?>(null)

    val colorBars = mutableListOf(
        false
    )

    val currentColor
        get() = _currentColor

    val barColor
        get() = _barColor

    fun updateCurrentColor(colorEnvelope: ColorEnvelope?){
        _currentColor.update {
            colorEnvelope
        }
    }

    fun updateBarColor(color : Color?){
        _barColor.update{
            color
        }
    }

    lateinit var clickAudio: MediaPlayer
    lateinit var guessAudio: MediaPlayer

    val currentPlayer = "Abc"
    val guessWord = "Y_u_ W_r_"
    val randomWord = "Your word" //for drawer screen

    var serverCommunicationViewModel: ServerCommunicationViewModel? = null

    var _isChatActive = MutableStateFlow(false)

    var isHintShowed =false

    val isChatActive:StateFlow<Boolean>
        get() = _isChatActive.asStateFlow()


    fun initializeServerViewModel(communicationViewModel: ServerCommunicationViewModel){
        serverCommunicationViewModel = communicationViewModel
    }

    fun checkRoomAvailability(){
        CoroutineScope(Dispatchers.IO).launch {

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

        return Player(name = playerName, joinType = joinType, roomName = roomName, roomPass = roomPass, admin= admin, profile = profilePic)
    }

    fun audioIntializer(context: Context){
        clickAudio = MediaPlayer.create(context, R.raw.click)
        guessAudio = MediaPlayer.create(context, R.raw.guess)
    }

    fun clickAudioPlayer() {
        CoroutineScope(Dispatchers.Default).launch {
            clickAudio.start()
        }
    }

    fun guessAudioPlayer() {
//        CoroutineScope(Dispatchers.Default).launch {
            guessAudio.start()
//        }
    }
}