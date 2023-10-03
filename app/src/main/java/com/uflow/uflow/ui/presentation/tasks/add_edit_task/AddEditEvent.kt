package com.uflow.uflow.ui.presentation.tasks.add_edit_task

sealed interface AddEditEvent {
    object ClearAllField: AddEditEvent
    object SaveTask: AddEditEvent
    data class OnTitleChange(val title: String): AddEditEvent
    data class OnDescriptionChange(val description: String): AddEditEvent
    data class OnCategoryChange(val category: String): AddEditEvent
    data class OnPriorityChange(val priority: Int): AddEditEvent
    data class GetTaskToEdit(val id: Int): AddEditEvent
}