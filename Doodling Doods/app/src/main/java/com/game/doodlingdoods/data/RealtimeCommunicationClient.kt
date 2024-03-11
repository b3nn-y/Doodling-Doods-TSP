package com.game.doodlingdoods.data

import com.game.doodlingdoods.GameApi.KtorServerApi
import kotlinx.coroutines.flow.Flow

interface RealtimeCommunicationClient {
    fun getStateStream(url: String = "ws://${KtorServerApi.serverIp}:8080", path:String): Flow<String>
    suspend fun sendAction(message: String)
    suspend fun close()
}