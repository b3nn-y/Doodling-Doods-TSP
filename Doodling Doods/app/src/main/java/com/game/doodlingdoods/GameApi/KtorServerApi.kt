package com.game.doodlingdoods.GameApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KtorServerApi {
    val serverIp ="10.51.25.233"
    val port = "8844"

    val api:KtorServer by lazy {
        Retrofit.Builder()
            .baseUrl("http://${serverIp}:${port}")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KtorServer::class.java)
    }
}