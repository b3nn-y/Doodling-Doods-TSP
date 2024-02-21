package com.game.doodlingdoods.GameApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KtorServerApi {
    val api:KtorServer by lazy {
        Retrofit.Builder()
            .baseUrl("https://774d-122-15-156-180.ngrok-free.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KtorServer::class.java)
    }
}