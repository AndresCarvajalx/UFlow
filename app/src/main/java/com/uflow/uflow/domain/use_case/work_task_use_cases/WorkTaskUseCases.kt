package com.uflow.uflow.domain.use_case.work_task_use_cases

data class WorkTaskUseCases(
    val addWorkTaskUseCase: AddWorkTaskUseCase,
    val deleteWorkTaskUseCase: DeleteWorkTaskUseCase,
    val getWorkTasksUseCase: GetWorkTasksUseCase,
    val getWorkTaskByIdUseCase: GetWorkTaskByIdUseCase,
    val updateWorkTaskUseCase: UpdateWorkTaskUseCase,
)