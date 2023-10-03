package com.uflow.uflow.ui.presentation.timer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassFull
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.HourglassTop
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.ui.graphics.vector.ImageVector

data class TimerSection(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
){
    companion object {
        const val COUNTDOWN = "Pomodoro"
        const val STOPWATCH = "Stopwatch"

        val timerSections = listOf<TimerSection>(
            TimerSection(
                title = COUNTDOWN,
                selectedIcon = Icons.Filled.HourglassFull,
                unselectedIcon = Icons.Outlined.HourglassTop
            ),
            /*
            TimerSection(
              title = STOPWATCH,
                selectedIcon = Icons.Filled.Timer,
                unselectedIcon = Icons.Outlined.Timer
            ),*/
        )
    }
}
