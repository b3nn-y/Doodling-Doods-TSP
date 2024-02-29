package com.game.doodlingdoods.roomDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities =[LoginEntity::class], version = 4 )
abstract class LoginDatabase: RoomDatabase() {

    abstract val loginDao:LoginDao

    companion object{
        @Volatile
        private var INSTANCE:LoginDatabase ?=null
        fun getInstance(context: Context):LoginDatabase{
            synchronized(this){
                var tempInstance = INSTANCE
                if (tempInstance==null){
                    tempInstance= Room.databaseBuilder(
                        context.applicationContext,
                        LoginDatabase::class.java,
                        "login_database"
                    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                }

                INSTANCE = tempInstance

                return tempInstance
            }
        }
    }
}