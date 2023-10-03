package com.uflow.uflow.domain.use_case.task_use_cases

import com.uflow.uflow.domain.models.Task
import com.uflow.uflow.domain.respository.TaskRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task){
        repository.updateTask(task)
    }
}