package com.uflow.uflow.ui.presentation.timer.countdown

import android.content.Context
import android.media.MediaPlayer
import android.os.CountDownTimer
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uflow.uflow.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class CountdownViewModel @Inject constructor() : ViewModel() {

    private lateinit var onTimeFinish: MediaPlayer
    private lateinit var onBreakFinish: MediaPlayer
    private val isOnBreak = mutableStateOf(false)
    val isStarting = mutableStateOf(false)
    val isPaused = mutableStateOf(false)

    private val breakTime = mutableLongStateOf(600000)
    private val studyTime = mutableLongStateOf(3000000)
    private val countdownState = mutableLongStateOf((studyTime.longValue))
    private val reaming = mutableLongStateOf(countdownState.longValue)
    val reamingText = mutableStateOf(reaming.longValue.toString())

    init {
        getFormattedTime(reaming.longValue)
    }

    private var countDownTimer: CountDownTimer? = null

    fun onEvent(event: CountdownEvent){
        when(event){
            is CountdownEvent.OnSave -> {
                breakTime.longValue = (event.breakTime * 60 * 1000).toLong()
                studyTime.longValue = (event.studyTime * 60 * 1000).toLong()
                countdownState.longValue = studyTime.longValue
                onCancel()
                getFormattedTime(countdownState.longValue)
            }

            CountdownEvent.OnPomodoroDefault -> {
                studyTime.longValue = 3000000
                breakTime.longValue = 600000
                countdownState.longValue = studyTime.longValue
                onCancel()
                getFormattedTime(countdownState.longValue)
            }
        }
    }
    fun createMediaPlayer(context: Context) {
        onTimeFinish = MediaPlayer.create(context, R.raw.alarmclock)
        onBreakFinish = MediaPlayer.create(context, R.raw.alarmclock2)
    }

    fun onStart() {
        isStarting.value = true
        isPaused.value = false
        getFormattedTime(countdownState.longValue)
        countDownTimer = object : CountDownTimer(countdownState.longValue, 1000) {
            override fun onTick(p0: Long) {
                reaming.longValue = p0
                getFormattedTime(reaming.longValue)
            }

            override fun onFinish() {
                isOnBreak.value = !isOnBreak.value
                if (isOnBreak.value) {
                    onBreakFinish.start()
                    onStartBreak()
                } else {
                    isStarting.value = false
                    isPaused.value = false
                    isOnBreak.value = false
                    countdownState.longValue = studyTime.longValue
                    reaming.longValue = countdownState.longValue
                    getFormattedTime(reaming.longValue)
                    onTimeFinish.start()

                }
            }
        }.start()
    }

    fun onCancel() {
        countdownState.longValue = studyTime.longValue
        reaming.longValue = countdownState.longValue
        getFormattedTime(reaming.longValue)
        isStarting.value = false
        isPaused.value = false
        isOnBreak.value = false
        countDownTimer?.cancel()
    }

    fun onResume() {
        countdownState.longValue = reaming.longValue
        onStart()
    }

    fun onPause() {
        countdownState.longValue = reaming.longValue
        isStarting.value = false
        isPaused.value = true
        countDownTimer?.cancel()
    }

    private fun onStartBreak() {
        countdownState.longValue = breakTime.longValue
        onStart()
    }

    private fun getFormattedTime(millis: Long) {
        val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        reamingText.value = formatter.format(Date(millis))
    }
}