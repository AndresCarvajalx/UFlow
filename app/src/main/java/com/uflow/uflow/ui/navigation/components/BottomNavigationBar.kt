package com.uflow.uflow.ui.navigation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.uflow.uflow.ui.navigation.Screen
import java.util.Locale

@Composable
fun BottomNavigationBar(
    items: List<Screen>,
    navController: NavHostController
) {
    NavigationBar(
        tonalElevation = 5.dp,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            NavigationBarItem(
                label = {
                    Text(
                        text = screen.title.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ROOT
                            ) else it.toString()
                        },
                        textAlign = TextAlign.Center,
                    )
                },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        screen.icon?.let {
                            Icon(
                                it,
                                contentDescription = screen.title
                            )
                        }
                    }
                },
                selected = currentDestination?.route == screen.route,
                alwaysShowLabel = true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}