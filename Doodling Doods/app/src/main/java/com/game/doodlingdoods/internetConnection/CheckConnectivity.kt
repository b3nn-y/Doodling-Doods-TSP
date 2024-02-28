package com.game.doodlingdoods.internetConnection

import kotlinx.coroutines.flow.Flow

interface CheckConnectivity {


        fun observe(): Flow<InternetStatus>

        enum class InternetStatus {
            Unavailable, Available, Losing, Lost
        }

}