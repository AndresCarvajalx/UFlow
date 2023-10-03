package com.uflow.uflow.ui.presentation.tasks

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uflow.uflow.domain.use_case.task_use_cases.TaskUseCases
import com.uflow.uflow.domain.utils.TaskOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases
): ViewModel() {

    private val _state = mutableStateOf(TaskState())
    var state = _state
    private var getTasksJob: Job? = null
    /*
    private val task = Task(title = "Hacer aseo", description = "This is my description, test only gotta show 2 line of text test only gotta show 2 line of text ", category = "hogar", priority = 9, isComplete = false, date = System.currentTimeMillis())
    private val task2 = Task(title = "Hacer lo de redes", description = "This is my description, test only gotta show 2 line of text test only gotta show 2 line of text ", category = "entretenimiento", priority = 1, isComplete = false, date = System.currentTimeMillis())
    private val task3 = Task(title = "Pagar factura", description = "This is my description, test only gotta show 2 line of text test only gotta show 2 line of text ", category = "personal", priority = 5, isComplete = false, date = System.currentTimeMillis())
    private val task4 = Task(title = "Hacer diapositivas", description = "This is my description, test only gotta show 2 line of text test only gotta show 2 line of text ", category = "estudio", priority = 8, isComplete = false, date = System.currentTimeMillis())
    private val task5 = Task(title = "Prueba sin descripcion", category = "estudio", priority = 9, isComplete = false, date = System.currentTimeMillis())
    */
    init {
        getTasks(TaskOrder.Date)
        /*
        viewModelScope.launch {
            taskUseCases.addTaskUseCase(task)
            taskUseCases.addTaskUseCase(task2)
            taskUseCases.addTaskUseCase(task3)
            taskUseCases.addTaskUseCase(task4)
            taskUseCases.addTaskUseCase(task5)
        }
         */
    }

    fun onEvent(event: TaskEvent){
        when(event) {
            is TaskEvent.DeleteTask -> {
                viewModelScope.launch {
                    taskUseCases.deleteTaskUseCase(event.task)
                }
            }
            is TaskEvent.DeleteAllTask -> viewModelScope.launch {
                viewModelScope.launch {
                    state.value.tasks.forEach { task ->
                        taskUseCases.deleteTaskUseCase(task)
                    }
                }
                _state.value = state.value.copy()
            }
            is TaskEvent.Order -> {
                if (state.value.taskOrder::class == event.order::class && state.value.taskOrder == event.order
                ) {
                    return
                }else {
                    viewModelScope.launch {
                        getTasks(event.order)
                    }
                }
            }

            is TaskEvent.ToggleOrderSection -> _state.value = state.value.copy(
                isOrderSectionVisible = !state.value.isOrderSectionVisible
            )

            is TaskEvent.UpdateTask -> viewModelScope.launch {
                viewModelScope.launch {
                    taskUseCases.updateTaskUseCase(event.task)
                    getTasks(event.order)
                }
            }
        }
    }

    private fun getTasks(taskOrder: TaskOrder){
        getTasksJob?.cancel()
        getTasksJob = viewModelScope.launch {
            taskUseCases.getTasksUseCase.invoke(
                taskOrder
            ).onEach { task ->
                _state.value = state.value.copy(
                    tasks = task,
                    taskOrder= taskOrder
                )
            }.launchIn(viewModelScope)
        }
    }
}