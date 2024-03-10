package com.game.doodlingdoods.roomDb

data class AuthDataClass (val sid:Int, val isAuthorized:Boolean)

data class JsonUser(val sid: Int,val user_name:String,val mailId_by:String)

data class LeaderBoardDataClass(
    val id: Int,
    val user_name: String,
    val matches_played: Int,
    val trophies_count: Int
)