package com.uflow.uflow.ui.presentation.work_tasks

import com.uflow.uflow.domain.models.WorkTask

sealed interface WorkTaskEvent {
    object OnDeleteAllWorkTask : WorkTaskEvent
    data class OnDeleteWorkTask(val workTask: WorkTask): WorkTaskEvent
    data class OnCheck(val workTask: WorkTask): WorkTaskEvent
    data class OnSearch(val toSearch: String): WorkTaskEvent
}