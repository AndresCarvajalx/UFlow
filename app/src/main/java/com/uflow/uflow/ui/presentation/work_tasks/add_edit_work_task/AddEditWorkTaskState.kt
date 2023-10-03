package com.uflow.uflow.ui.presentation.work_tasks.add_edit_work_task

import java.time.LocalDate
import java.time.LocalTime

data class AddEditWorkTaskState(
    val id: Int = -1,
    val className: String = "",
    val assignment: String = "",
    val subject: String = "",
    val description: String = "",
    val priority: Int = 1,
    val toDoDate: LocalDate = LocalDate.now(),
    val toDoTime: LocalTime = LocalTime.now(),
    val toDeliveryDate: LocalDate = LocalDate.now().plusDays(1),
    val toDeliveryTime: LocalTime = LocalTime.now(),
    val icon: Int = 0
)
