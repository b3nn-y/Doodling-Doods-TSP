package com.game.doodlingdoods.data

import kotlinx.coroutines.flow.Flow

interface RealtimeCommunicationClient {
    fun getStateStream(url: String = "ws://10.52.0.178:8080", path:String): Flow<String>
    suspend fun sendAction(message: String)
    suspend fun close()
}