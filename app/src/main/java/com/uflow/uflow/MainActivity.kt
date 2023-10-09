package com.uflow.uflow

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.uflow.uflow.ui.presentation.work_tasks.HomeworkScreen
import com.uflow.uflow.ui.presentation.setting.SettingScreen
import com.uflow.uflow.ui.presentation.tasks.TaskScreen
import com.uflow.uflow.ui.theme.UFlowTheme
import com.uflow.uflow.ui.presentation.timer.TimerScreen
import com.uflow.uflow.ui.navigation.Screen
import com.uflow.uflow.ui.navigation.Screen.Companion.items
import com.uflow.uflow.ui.navigation.components.BottomNavigationBar
import com.uflow.uflow.ui.navigation.components.FloatingButtonNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UFlowTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        floatingActionButton = { FloatingButtonNavigation() },
                        bottomBar = {
                            BottomNavigationBar(
                                items = items,
                                navController = navController
                            )
                        },
                    ) { innerPadding ->
                        NavHost(
                            navController,
                            startDestination = Screen.TasksScreen.route,
                            Modifier.padding(innerPadding)
                        ) {
                            composable(Screen.HomeworkScreen.route) { HomeworkScreen(navController) }
                            composable(Screen.TasksScreen.route) { TaskScreen() }
                            composable(Screen.TimerScreen.route) { TimerScreen(applicationContext) }
                            composable(Screen.SettingScreen.route) { SettingScreen(navController) }
                        }
                    }
                }
            }
        }
    }
}
