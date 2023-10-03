package com.uflow.uflow.ui.presentation.timer.stopwatch

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StopwatchViewModel @Inject constructor (): ViewModel() {

    private var isRunning: Boolean = false
    private val _state = mutableListOf<String>("00:00:00")
    var state = _state

    fun startStopwatch(
        time: String
    ){
        if(isRunning) return

    }

    fun stopStopwatch(){
        isRunning = false
    }
}