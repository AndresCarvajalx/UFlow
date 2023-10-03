package com.uflow.uflow.ui.presentation.work_tasks

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uflow.uflow.domain.models.WorkTask
import com.uflow.uflow.domain.use_case.work_task_use_cases.WorkTaskUseCases
import com.uflow.uflow.domain.utils.NotificationBroadcast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class WorkTaskViewModel @Inject constructor(
    private val workTaskUseCases: WorkTaskUseCases
) : ViewModel() {

    private var getTasksJob: Job? = null
    private val _state = mutableStateOf(WorkTaskState())
    var state = _state

    init {
        getWorkTasks()
    }

    fun onEvent(event: WorkTaskEvent) {
        when (event) {
            is WorkTaskEvent.OnDeleteWorkTask -> {
                viewModelScope.launch {
                    workTaskUseCases.deleteWorkTaskUseCase(event.workTask)
                }
            }

            is WorkTaskEvent.OnCheck -> {
                viewModelScope.launch {
                    val work = WorkTask(
                        id = event.workTask.id,
                        className = event.workTask.className,
                        assignment = event.workTask.assignment,
                        subject = event.workTask.subject,
                        description = event.workTask.description,
                        priority = event.workTask.priority,
                        toDoDate = event.workTask.toDoDate,
                        toDoTime = event.workTask.toDoTime,
                        toDeliveryDate = event.workTask.toDeliveryDate,
                        toDeliveryTime = event.workTask.toDeliveryTime,
                        icon = event.workTask.icon,
                        isComplete = !event.workTask.isComplete
                    )
                    workTaskUseCases.updateWorkTaskUseCase(work)
                }
            }

            is WorkTaskEvent.OnSearch -> {
                if (event.toSearch.isBlank()) {
                    getWorkTasks()
                } else {
                    _state.value = state.value.copy(
                        workTasks = state.value.workTasks.sortedByDescending {
                            it.className.lowercase().contains(event.toSearch.lowercase()) ||
                                    it.description.lowercase()
                                        .contains(event.toSearch.lowercase()) ||
                                    it.subject.lowercase().contains(event.toSearch.lowercase()) ||
                                    it.assignment.lowercase().contains(event.toSearch.lowercase())
                        }
                    )
                }
            }

            WorkTaskEvent.OnDeleteAllWorkTask -> {
                viewModelScope.launch {
                    state.value.workTasks.forEach { workTask ->
                        workTaskUseCases.deleteWorkTaskUseCase(workTask)
                    }
                }
            }
        }
    }

    private fun getWorkTasks() {
        getTasksJob?.cancel()
        getTasksJob = viewModelScope.launch {
            workTaskUseCases.getWorkTasksUseCase.invoke().onEach { workTask ->
                _state.value = state.value.copy(
                    workTasks = workTask,
                )
            }.launchIn(viewModelScope)
        }
    }
}