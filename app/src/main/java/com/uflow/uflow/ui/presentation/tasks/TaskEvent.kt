package com.uflow.uflow.ui.presentation.tasks

import com.uflow.uflow.domain.models.Task
import com.uflow.uflow.domain.utils.TaskOrder

sealed interface TaskEvent {
    data class DeleteTask(val task: Task) : TaskEvent
    data class UpdateTask(val task: Task, val order: TaskOrder): TaskEvent
    data class Order(val order: TaskOrder): TaskEvent
    object ToggleOrderSection: TaskEvent
    object DeleteAllTask : TaskEvent
}