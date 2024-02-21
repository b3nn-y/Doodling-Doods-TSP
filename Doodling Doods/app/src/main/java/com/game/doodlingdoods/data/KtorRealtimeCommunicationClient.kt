package com.game.doodlingdoods.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull

class KtorRealtimeCommunicationClient(
    private val client: HttpClient
): RealtimeCommunicationClient {

    private var session: WebSocketSession? = null

    override fun getStateStream(url: String, path: String): Flow<String> {
        return flow {
            session = client.webSocketSession {
                url(url + path)
            }
            val messageStates = session!!
                .incoming
                .consumeAsFlow()
                .filterIsInstance<Frame.Text>()
                .mapNotNull {
                    it.readText()
                }

            emitAll(messageStates)
        }
    }

    override suspend fun sendAction(message: String) {
        session?.outgoing?.send(
            Frame.Text(message)
        )
    }

    override suspend fun close() {
        session?.close()
        session = null
    }


}