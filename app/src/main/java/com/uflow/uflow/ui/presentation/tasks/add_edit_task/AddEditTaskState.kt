package com.uflow.uflow.ui.presentation.tasks.add_edit_task

data class AddEditTaskState(
    val id: Int = -1,
    val title: String = "",
    val description: String = "",
    val priority: Int = 1,
    val category: String = "",
)