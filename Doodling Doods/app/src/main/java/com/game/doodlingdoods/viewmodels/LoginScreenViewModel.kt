package com.game.doodlingdoods.viewmodels

import android.util.JsonReader
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.game.doodlingdoods.GameApi.KtorServerApi
import com.game.doodlingdoods.roomDb.JsonUser
import com.game.doodlingdoods.roomDb.LoginDao
import com.game.doodlingdoods.roomDb.LoginDatabase
import com.game.doodlingdoods.roomDb.LoginEntity
import com.game.doodlingdoods.utils.PasswordHash
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {

    private val passwordHash = PasswordHash()

    private var _isSignInSuccess = MutableStateFlow(false)
    val isSignInSuccess:StateFlow<Boolean>
        get() = _isSignInSuccess.asStateFlow()

    var  playerName:String?=null
     var playerMailId:String?=null

    fun signInWithCredentials(mailId: String, password: String) {
        viewModelScope.launch {
            try {
                val hashedPassword = passwordHash.hashPassword(password)
                val response = KtorServerApi.api.signInWithCredentials(mailId, hashedPassword)
                //check here for response body is authorized or not //Todo
                if (response.isSuccessful && response.body()?.isAuthorized == true) _isSignInSuccess.value = true
                else Log.i("Response", response.errorBody().toString())

            } catch (e: Exception) {
                Log.i("Login", e.toString())
            }

        }
    }

    fun userInputFilter(mailId: String, password: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9.]+@[a-zA-Z0-9.]+\\.[a-zA-Z]{2,}")
        val passwordRegex =
            Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+\$).{8,}\$")


        return mailId.matches(emailRegex) && password.matches(passwordRegex)


    }

    fun getUserInfo(mailId: String){

        viewModelScope.launch {
            val response = KtorServerApi.api.getUserInfo(mail_id = mailId)
            if (response.isSuccessful && response.body()!=null){
                Log.i("ResponseBody",response.body().toString())

                playerName = response.body()?.user_name
                playerMailId= response.body()?.mailId_by

                Log.i("Response**",playerName+playerMailId)

            }else{

                Log.i("ResponseVV",response.toString())
            }
        }
    }



}