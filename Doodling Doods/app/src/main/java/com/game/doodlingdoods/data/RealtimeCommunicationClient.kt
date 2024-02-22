package com.game.doodlingdoods.data

import kotlinx.coroutines.flow.Flow

interface RealtimeCommunicationClient {
    fun getStateStream(url: String = "ws://192.168.139.210:8080", path:String): Flow<String>
    suspend fun sendAction(message: String)
    suspend fun close()
}