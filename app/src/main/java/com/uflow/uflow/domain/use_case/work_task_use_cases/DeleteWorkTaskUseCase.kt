package com.uflow.uflow.domain.use_case.work_task_use_cases

import com.uflow.uflow.domain.models.Task
import com.uflow.uflow.domain.models.WorkTask
import com.uflow.uflow.domain.respository.TaskRepository
import com.uflow.uflow.domain.respository.WorkTaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class DeleteWorkTaskUseCase(
    private val repository: WorkTaskRepository
) {
    suspend operator fun invoke(workTask: WorkTask){
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteWorkTask(workTask)
        }
    }
}