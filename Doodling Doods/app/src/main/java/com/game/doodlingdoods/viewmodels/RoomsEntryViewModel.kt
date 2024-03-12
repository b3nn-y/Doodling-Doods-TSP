package com.game.doodlingdoods.viewmodels

import androidx.lifecycle.ViewModel
import com.game.doodlingdoods.GameApi.KtorServerApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.BufferedReader
import java.io.InputStreamReader

class RoomsEntryViewModel:ViewModel() {
    val _isHintActive =MutableStateFlow(true)
    val isHintActive:StateFlow<Boolean>
        get() = _isHintActive.asStateFlow()

    fun pingServer(): Boolean {
        val serverAddress: String =KtorServerApi.serverIp

        var isSuccess = false
        try {
            val process = Runtime.getRuntime().exec("/system/bin/ping -c 1 $serverAddress")
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val stringBuilder = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                stringBuilder.append(line).append("\n")
            }
            val returnCode = process.waitFor()
            isSuccess = returnCode == 0
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return isSuccess
    }


}