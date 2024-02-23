package com.game.doodlingdoods.GameApi

import com.game.doodlingdoods.filesForServerCommunication.QueryRoom
import com.game.doodlingdoods.filesForServerCommunication.RoomAvailability
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface KtorServer {
    @GET("/rooms")
    suspend fun getAllRooms():Response<List<RoomDetailsDataClass>>

    @FormUrlEncoded
    @POST("/room")
    suspend fun checkRoom(@Field("room_id") room_id: String): Response<RoomAvailability>
}