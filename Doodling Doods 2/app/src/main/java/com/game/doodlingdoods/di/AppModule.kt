package com.game.doodlingdoods.di

import com.game.doodlingdoods.data.KtorRealtimeCommunicationClient
import com.game.doodlingdoods.data.RealtimeCommunicationClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(Logging)
            install(WebSockets)
        }
    }

    @Singleton
    @Provides
    fun provideRealtimeMessagingClient(httpClient: HttpClient): RealtimeCommunicationClient {
        return KtorRealtimeCommunicationClient(httpClient)
    }
}