package com.game.doodlingdoods.viewmodels


import android.util.Log


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.doodlingdoods.GameApi.KtorServerApi

import com.game.doodlingdoods.utils.PasswordHash

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



class SignUpScreenViewModel : ViewModel() {

    private var _isSignUpSuccess = MutableStateFlow(false)
    private val passwordHash = PasswordHash()


    val  isSignUpSuccess :StateFlow<Boolean>
        get() = _isSignUpSuccess.asStateFlow()
    fun signUpWithCredentials(userName: String, mailId: String, password: String) {
        viewModelScope.launch {
            try {
                val hashedPassword =  passwordHash.hashPassword(password)

                Log.i("hash",hashedPassword)
                Log.i("request","$userName, $mailId, $hashedPassword")
                val response = KtorServerApi.api.signInWithCredentials(userName, mailId, hashedPassword)
                Log.i("Api","Sign up call ${response.body().toString()}")

                if (response.isSuccessful && response.body()?.isAuthorized==true){

                    _isSignUpSuccess.value = true



                    println(isSignUpSuccess.value)
                }
                else {
                    Log.i("Response", response.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.e("Exception",e.toString())
            }


        }
    }


    fun userInputFilter(userName: String, mailId: String, password: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9.]+@[a-zA-Z0-9.]+\\.[a-zA-Z]{2,}")
        val passwordRegex =
            Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+\$).{8,}\$")
        val usernameRegex = Regex("^[a-zA-Z][a-zA-Z0-9_-]{2,19}\$")

        return userName.matches(usernameRegex) &&
                mailId.matches(emailRegex) && password.matches(passwordRegex)


    }



}