package com.game.doodlingdoods.roomDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login_status_table")
data class LoginEntity (
    @PrimaryKey(autoGenerate = true)
    val sid: Int = 0,

    @ColumnInfo(name = "is_logged_in")
    val is_logged_in: Boolean,
)