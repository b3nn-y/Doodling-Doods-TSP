package com.game.doodlingdoods.GameApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KtorServerApi {
    val serverIp ="10.15.217.168"

    val api:KtorServer by lazy {
        Retrofit.Builder()
            .baseUrl("http://${serverIp}:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KtorServer::class.java)
    }
}