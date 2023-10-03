package com.uflow.uflow.domain.use_case.work_task_use_cases

import com.uflow.uflow.domain.models.WorkTask
import com.uflow.uflow.domain.respository.WorkTaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach

class GetWorkTasksUseCase(
    private val repository: WorkTaskRepository
) {
    operator fun invoke(): Flow<List<WorkTask>>{
        return repository.getWorkTasks()
    }
}