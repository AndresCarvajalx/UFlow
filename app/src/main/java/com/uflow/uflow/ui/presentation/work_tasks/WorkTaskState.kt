package com.uflow.uflow.ui.presentation.work_tasks

import com.uflow.uflow.domain.models.WorkTask

data class WorkTaskState(
    val workTasks: List<WorkTask> = emptyList<WorkTask>()
)