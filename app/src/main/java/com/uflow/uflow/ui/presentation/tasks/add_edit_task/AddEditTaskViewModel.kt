package com.uflow.uflow.ui.presentation.tasks.add_edit_task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uflow.uflow.domain.models.Task
import com.uflow.uflow.domain.use_case.task_use_cases.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases
): ViewModel() {

    // TODO USE CHANGE VALUE OF _STATE TO STATE WITH COPY
    private val _state = mutableStateOf(AddEditTaskState())
    var state = _state

    var id by mutableIntStateOf(state.value.id)
    var title by mutableStateOf(state.value.title)
    var description by mutableStateOf(state.value.description)
    var category by mutableStateOf(state.value.category)
    var priority by mutableIntStateOf(state.value.priority)

    private lateinit var task: Task

    fun onEvent(event: AddEditEvent){
        when(event){
            AddEditEvent.ClearAllField -> {
                title = ""
                description = ""
                category = ""
                priority = 0
            }
            is AddEditEvent.SaveTask -> viewModelScope.launch{
                if(title.isBlank()){
                    return@launch
                }else {
                    if(id == -1){
                        task = Task(
                            title = title,
                            description = description,
                            priority = priority,
                            category = category,
                            date = System.currentTimeMillis()
                        )
                        viewModelScope.launch {
                            taskUseCases.addTaskUseCase(task)
                        }
                    }else {
                        task = Task(
                            id= id,
                            title = title,
                            description = description,
                            priority = priority,
                            category = category,
                            date = System.currentTimeMillis()
                        )
                        viewModelScope.launch {
                            taskUseCases.updateTaskUseCase(task)
                        }
                        id = -1
                    }
                }
            }
            is AddEditEvent.OnCategoryChange -> category = event.category
            is AddEditEvent.OnDescriptionChange -> description = event.description
            is AddEditEvent.OnPriorityChange -> priority = event.priority
            is AddEditEvent.OnTitleChange -> title = event.title
            is AddEditEvent.GetTaskToEdit -> viewModelScope.launch {
                task = taskUseCases.getTaskUseCase(event.id)!!
                title = task.title
                description = task.description
                category = task.category
                priority = task.priority
            }
        }
    }

}