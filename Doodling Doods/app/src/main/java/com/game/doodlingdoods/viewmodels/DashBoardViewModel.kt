package com.game.doodlingdoods.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.doodlingdoods.GameApi.KtorServerApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashBoardViewModel:ViewModel() {

    private var _isResponseReceived= MutableStateFlow(false)

    val isResonponseReceived:StateFlow<Boolean>
        get() = _isResponseReceived.asStateFlow()

    val leaderBoardHashMap =HashMap<String,Int>()


    fun getLeaderBoardDetails(){
        viewModelScope.launch {
            val response = KtorServerApi.api.getLeaderBoard()
            if (response.isSuccessful && response.body() !=null){
                Log.i("ResponseDash",response.body().toString())
                response.body()!!.forEach { player ->
                    leaderBoardHashMap.put(player.user_name,player.trophies_count)
                }
                Log.i("ResponseDash",leaderBoardHashMap.toString())
            }
        }
    }
}