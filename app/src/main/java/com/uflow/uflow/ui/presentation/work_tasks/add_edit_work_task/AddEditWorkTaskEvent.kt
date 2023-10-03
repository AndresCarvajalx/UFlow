package com.uflow.uflow.ui.presentation.work_tasks.add_edit_work_task

import android.content.Context
import java.time.LocalDate
import java.time.LocalTime

sealed interface AddEditWorkTaskEvent {
    object ClearAllFields: AddEditWorkTaskEvent
    data class SaveWorkTask(val context: Context): AddEditWorkTaskEvent
    data class GetWorkTaskToEdit(val id: Int): AddEditWorkTaskEvent
    data class OnClassNameChange(val className: String): AddEditWorkTaskEvent
    data class OnAssignmentChange(val assignment: String): AddEditWorkTaskEvent
    data class OnSubjectChange(val subject: String): AddEditWorkTaskEvent
    data class OnDescriptionChange(val description: String): AddEditWorkTaskEvent
    data class OnPriorityChange(val priority: Int): AddEditWorkTaskEvent
    data class OnToDoDateChange(val toDoDate: LocalDate): AddEditWorkTaskEvent
    data class OnToDoTimeChange(val toDoTime: LocalTime): AddEditWorkTaskEvent
    data class OnToDeliveryDateChange(val toDeliveryDate: LocalDate): AddEditWorkTaskEvent
    data class OnToDeliveryTimeChange(val toDeliveryTime: LocalTime): AddEditWorkTaskEvent
    data class OnIconChange(val icon: Int): AddEditWorkTaskEvent
}