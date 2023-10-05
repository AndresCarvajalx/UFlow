package com.uflow.uflow.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title:String = "", val icon: ImageVector){
    object TasksScreen: Screen("task_screen", "Task", icon= Icons.Filled.Checklist)
    object TimerScreen: Screen("timer_screen", "Timer", icon=Icons.Filled.Timer)
    object HomeworkScreen: Screen("home_work_screen", "Work", icon=Icons.Filled.CalendarMonth)
    object SettingScreen: Screen("setting_screen", "Setting", icon=Icons.Filled.Settings)

    companion object {
        val items: List<Screen> = listOf(
            HomeworkScreen,
            TasksScreen,
            TimerScreen,
            SettingScreen,
        )
    }
}
