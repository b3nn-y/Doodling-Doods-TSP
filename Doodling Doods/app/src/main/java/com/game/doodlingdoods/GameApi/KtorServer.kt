package com.game.doodlingdoods.GameApi

import com.game.doodlingdoods.filesForServerCommunication.RoomAvailability
import com.game.doodlingdoods.users.AuthenticationDataClass
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface KtorServer {
    @GET("/rooms")
    suspend fun getAllRooms(): Response<List<RoomDetailsDataClass>>

    @FormUrlEncoded
    @POST("/room")
    suspend fun checkRoom(@Field("room_id") room_id: String): Response<RoomAvailability>

    @FormUrlEncoded
    @POST("/signup")

    suspend fun signInWithCredentials(
        @Field("user_name") user_name: String,
        @Field("mail_id") mail_id: String,
        @Field("password") password:String
    ):Response<AuthenticationDataClass>

    @FormUrlEncoded
    @POST("/signin")
    suspend fun signInWithCredentials(
        @Field("mail_id") mail_id: String,
        @Field("password") password:String
    ):Response<AuthenticationDataClass>
}