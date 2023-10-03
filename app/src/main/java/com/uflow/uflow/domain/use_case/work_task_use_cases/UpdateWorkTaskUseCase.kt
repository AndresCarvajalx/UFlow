package com.uflow.uflow.domain.use_case.work_task_use_cases

import com.uflow.uflow.domain.models.Task
import com.uflow.uflow.domain.models.WorkTask
import com.uflow.uflow.domain.respository.TaskRepository
import com.uflow.uflow.domain.respository.WorkTaskRepository
import javax.inject.Inject

class UpdateWorkTaskUseCase @Inject constructor(
    private val repository: WorkTaskRepository
) {
    suspend operator fun invoke(workTask: WorkTask){
        repository.updateWorkTask(workTask)
    }
}