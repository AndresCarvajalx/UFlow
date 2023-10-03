package com.uflow.uflow.ui.presentation.timer

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.uflow.uflow.ui.presentation.timer.TimerSection.Companion.timerSections
import com.uflow.uflow.ui.presentation.timer.countdown.Countdown
import com.uflow.uflow.ui.presentation.timer.stopwatch.Stopwatch
import com.uflow.uflow.ui.presentation.timer.stopwatch.StopwatchViewModel

@Composable
fun TimerScreen(
    context: Context
) {
    var indexTap by remember {
        mutableIntStateOf(0)
    }
    var isInTimerSection by remember {
        mutableStateOf(TimerSection.COUNTDOWN)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabRow(selectedTabIndex = indexTap) {
            timerSections.forEachIndexed { index, timerSection ->
                Tab(
                    selected = indexTap == index,
                    onClick = {
                        indexTap = index
                        isInTimerSection = timerSection.title
                    },
                    icon = {
                        Icon(
                            imageVector = if (indexTap == index) {
                                timerSection.selectedIcon
                            } else {
                                timerSection.unselectedIcon
                            }, contentDescription = null
                        )
                    },
                    text = {
                        Text(text = timerSection.title)
                    },
                )
            }
        }

        when (isInTimerSection) {
            TimerSection.STOPWATCH -> {
                Stopwatch()
            }
            TimerSection.COUNTDOWN -> {
                Countdown(
                    context= context,
                    modifier= Modifier.fillMaxSize()
                )
            }
        }
    }
}
