package com.game.doodlingdoods.GameApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KtorServerApi {
    val api:KtorServer by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.52.0.65:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KtorServer::class.java)
    }
}