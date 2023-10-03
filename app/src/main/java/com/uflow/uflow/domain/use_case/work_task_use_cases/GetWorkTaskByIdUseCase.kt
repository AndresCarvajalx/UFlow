package com.uflow.uflow.domain.use_case.work_task_use_cases

import com.uflow.uflow.domain.models.WorkTask
import com.uflow.uflow.domain.respository.WorkTaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GetWorkTaskByIdUseCase(
    private val repository: WorkTaskRepository
) {
    suspend operator fun invoke(id: Int): WorkTask? {
        return repository.getWorkTaskById(id)
    }
}