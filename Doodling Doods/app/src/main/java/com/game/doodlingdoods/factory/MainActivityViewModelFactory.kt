package com.game.doodlingdoods.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.game.doodlingdoods.viewmodels.MainActivityViewModel

class MainActivityViewModelFactory(
    val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainActivityViewModel::class.java)){
            return MainActivityViewModel(context = context) as T
        }
        throw IllegalArgumentException("wrong class!")
    }

}