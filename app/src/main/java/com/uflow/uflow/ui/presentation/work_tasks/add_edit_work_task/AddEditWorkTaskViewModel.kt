package com.uflow.uflow.ui.presentation.work_tasks.add_edit_work_task

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uflow.uflow.domain.models.WorkTask
import com.uflow.uflow.domain.use_case.work_task_use_cases.WorkTaskUseCases
import com.uflow.uflow.domain.utils.NotificationBroadcast
import com.uflow.uflow.domain.utils.NotificationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class AddEditWorkTaskViewModel @Inject constructor(
    private val workTaskUseCases: WorkTaskUseCases
) : ViewModel() {
    private val _state = mutableStateOf(AddEditWorkTaskState())
    private var state = _state

    var id by mutableIntStateOf(state.value.id)
    var className by mutableStateOf(state.value.className)
    var assignment by mutableStateOf(state.value.assignment)
    var subject by mutableStateOf(state.value.subject)
    var description by mutableStateOf(state.value.description)
    var priority by mutableIntStateOf(state.value.priority)
    var toDoDate by  mutableStateOf(state.value.toDoDate)
    var toDoTime by  mutableStateOf(state.value.toDoTime)
    var toDeliveryDate by mutableStateOf(state.value.toDeliveryDate)
    var toDeliveryTime by mutableStateOf(state.value.toDeliveryTime)

    var icon by mutableIntStateOf(state.value.icon)
    private lateinit var workTask: WorkTask

    fun onEvent(event: AddEditWorkTaskEvent) {
        when (event) {
            AddEditWorkTaskEvent.ClearAllFields -> {
                className = ""
                assignment = ""
                subject = ""
                description = ""
                priority = 1
                toDoDate = LocalDate.now()
                toDoTime = LocalTime.now()
                toDeliveryDate = LocalDate.now().plusDays(1)
                toDeliveryTime = LocalTime.now()
                icon = 0
            }

            is AddEditWorkTaskEvent.SaveWorkTask -> {
                if (className.isBlank() || subject.isBlank() || assignment.isBlank() ||
                    toDeliveryDate == LocalDate.now()
                ) {
                    return
                } else {
                    viewModelScope.launch {
                        if (id == -1) {
                            workTask = WorkTask(
                                className = className,
                                assignment = assignment,
                                subject = subject,
                                description = description,
                                priority = priority,
                                toDoDate = toDoDate,
                                toDoTime = toDoTime,
                                toDeliveryDate = toDeliveryDate,
                                toDeliveryTime = toDeliveryTime,
                                icon = icon
                            )
                            workTaskUseCases.addWorkTaskUseCase(workTask)
                        }else {
                            workTask = WorkTask(
                                id = id,
                                className = className,
                                assignment =assignment,
                                subject = subject,
                                description = description,
                                priority = priority,
                                toDoDate = toDoDate,
                                toDoTime = toDoTime,
                                toDeliveryDate = toDeliveryDate,
                                toDeliveryTime = toDeliveryTime,
                                icon = icon
                            )
                            workTaskUseCases.updateWorkTaskUseCase(workTask)
                        }
                    }
                    // TODO HACER QUE FUNCIONE TO DELIVERY
                    viewModelScope.launch {
                        NotificationUtil(event.context).setNotification(event.context, workTask, NotificationUtil.NOTIFICATION_TO_DO)
                    }
                }
            }

            is AddEditWorkTaskEvent.OnIconChange -> icon = event.icon
            is AddEditWorkTaskEvent.OnClassNameChange -> className = event.className
            is AddEditWorkTaskEvent.OnAssignmentChange -> assignment = event.assignment
            is AddEditWorkTaskEvent.OnDescriptionChange -> description = event.description
            is AddEditWorkTaskEvent.OnPriorityChange -> priority = event.priority
            is AddEditWorkTaskEvent.OnSubjectChange -> subject = event.subject
            is AddEditWorkTaskEvent.OnToDeliveryDateChange -> toDeliveryDate = event.toDeliveryDate
            is AddEditWorkTaskEvent.OnToDoDateChange -> toDoDate = event.toDoDate
            is AddEditWorkTaskEvent.GetWorkTaskToEdit -> {
                viewModelScope.launch {
                    workTask = workTaskUseCases.getWorkTaskByIdUseCase(event.id)!!
                    className = workTask.className
                    assignment = workTask.assignment
                    subject = workTask.subject
                    description = workTask.description
                    priority = workTask.priority
                    toDoDate = workTask.toDoDate
                    toDoTime = workTask.toDoTime
                    toDeliveryDate = workTask.toDeliveryDate
                    toDeliveryTime = workTask.toDeliveryTime
                    icon = workTask.icon
                }
            }

            is AddEditWorkTaskEvent.OnToDoTimeChange -> toDoTime = event.toDoTime
            is AddEditWorkTaskEvent.OnToDeliveryTimeChange -> toDeliveryTime = event.toDeliveryTime
        }
    }
}