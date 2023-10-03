package com.uflow.uflow.domain.use_case.task_use_cases

data class TaskUseCases(
    val addTaskUseCase: AddTaskUseCase,
    val deleteTaskUseCase: DeleteTaskUseCase,
    val getTasksUseCase: GetTasksUseCase,
    val getTaskUseCase: GetTaskUseCase,
    val updateTaskUseCase: UpdateTaskUseCase,
)