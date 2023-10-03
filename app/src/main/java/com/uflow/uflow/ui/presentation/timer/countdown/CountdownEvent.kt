package com.uflow.uflow.ui.presentation.timer.countdown

sealed interface CountdownEvent {
    data class OnSave(val breakTime: Int, val studyTime: Int) : CountdownEvent
    object OnPomodoroDefault : CountdownEvent
}