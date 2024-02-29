package com.game.doodlingdoods.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.game.doodlingdoods.roomDb.AuthDataClass
import com.game.doodlingdoods.roomDb.LoginDao
import com.game.doodlingdoods.roomDb.LoginDatabase
import com.game.doodlingdoods.roomDb.LoginEntity
import kotlin.math.log

class MainActivityViewModel(context: Context) : ViewModel() {

    private var loginDao: LoginDao
    private var isAlreadyLoginAdded = false

    init {
        loginDao = LoginDatabase.getInstance(context).loginDao
    }

    fun makeAsLoggedUser() {
        if (!isAlreadyLoginAdded) {
            loginDao.insertLoginStatus(LoginEntity(is_logged_in = true))
            isAlreadyLoginAdded = true
        }

    }

    fun convertEntityToData(): Boolean {
        val entities = getAlreadyLoggedInStatus()

        return if (entities.size > 0) {
            true

        } else {
            return false
        }


    }

    private fun getAlreadyLoggedInStatus(): List<LoginEntity> {
        return loginDao.getAllRow()
    }
}