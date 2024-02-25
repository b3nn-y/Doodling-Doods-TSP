package com.game.doodlingdoods.GameApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KtorServerApi {
    val api:KtorServer by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KtorServer::class.java)
    }
}