package com.uflow.uflow.domain.use_case.task_use_cases

import com.uflow.uflow.domain.models.Task
import com.uflow.uflow.domain.respository.TaskRepository

class GetTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: Int): Task? {
        return repository.getTaskById(id)
    }
}