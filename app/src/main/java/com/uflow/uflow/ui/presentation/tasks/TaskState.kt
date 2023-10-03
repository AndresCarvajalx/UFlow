package com.uflow.uflow.ui.presentation.tasks

import com.uflow.uflow.domain.models.Task
import com.uflow.uflow.domain.utils.TaskOrder

data class TaskState(
    val tasks: List<Task> = emptyList(),
    val taskOrder: TaskOrder = TaskOrder.Date,
    val isOrderSectionVisible: Boolean = false
)
