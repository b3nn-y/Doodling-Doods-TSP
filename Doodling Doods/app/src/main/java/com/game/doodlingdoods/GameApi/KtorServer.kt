package com.game.doodlingdoods.GameApi

import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface KtorServer {
    @GET("/rooms")
    suspend fun getAllRooms():Response<List<RoomDetailsDataClass>>

    @POST("/addRooms")
    suspend fun addRooms(@Body data:RoomDetailsDataClass)
}