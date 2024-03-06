package com.game.doodlingdoods.internetConnection

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {


        fun observe(): Flow<Status>

        enum class Status {
            Unavailable, Available, Losing, Lost
        }

}