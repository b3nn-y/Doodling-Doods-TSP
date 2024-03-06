package com.game.doodlingdoods.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DrawingScreenViewModel:ViewModel() {

    private var _isBottomSheetOpen = MutableStateFlow(true)
    val isBottomSheetOpen:StateFlow<Boolean>
        get() = _isBottomSheetOpen.asStateFlow()

    fun closeBottomSheet(){
        viewModelScope.launch {
            delay(5000)
            _isBottomSheetOpen.value = false
        }
    }
}