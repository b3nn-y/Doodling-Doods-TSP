package com.game.doodlingdoods.roomDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LoginDao {
    @Insert
    fun insertLoginStatus(isLoggedIn:LoginEntity)
    @Query("SELECT * FROM login_status_table")
    fun getAllRow(): List<LoginEntity>
}