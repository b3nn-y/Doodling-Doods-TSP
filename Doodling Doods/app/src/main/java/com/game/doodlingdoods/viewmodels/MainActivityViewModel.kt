package com.game.doodlingdoods.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.game.doodlingdoods.roomDb.LoginDao
import com.game.doodlingdoods.roomDb.LoginDatabase
import com.game.doodlingdoods.roomDb.LoginEntity

class MainActivityViewModel(context: Context) : ViewModel() {

    private var loginDao: LoginDao
    private var isAlreadyLoginAdded = false


    init {
        loginDao = LoginDatabase.getInstance(context).loginDao
    }

    fun makeAsLoggedUser(userName: String, mailId: String) {
        if (!isAlreadyLoginAdded) {
            loginDao.insertLoginStatus(
                LoginEntity(
                    is_logged_in = true,
                    user_name = userName,
                    mail_id = mailId
                )
            )
            isAlreadyLoginAdded = true
        }

    }

    fun getCurrentUserName(): String? {

        return loginDao.getFirstRow()?.user_name


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