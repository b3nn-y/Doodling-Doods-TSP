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

class LoginScreenViewModel : ViewModel() {
    private var _isSignInSuccess = MutableStateFlow(false)
    private val passwordHash = PasswordHash()
    val isSignInSuccess:StateFlow<Boolean>
        get() = _isSignInSuccess.asStateFlow()


    fun signInWithCredentials(mailId: String, password: String) {
        viewModelScope.launch {
            try {
                val hashedPassword = passwordHash.hashPassword(password)
                val response = KtorServerApi.api.signInWithCredentials(mailId, hashedPassword)
                if (response.isSuccessful) _isSignInSuccess.value = true
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
}