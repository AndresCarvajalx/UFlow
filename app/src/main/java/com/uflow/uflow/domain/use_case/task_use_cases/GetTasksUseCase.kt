package com.uflow.uflow.domain.use_case.task_use_cases

import com.uflow.uflow.domain.models.Task
import com.uflow.uflow.domain.respository.TaskRepository
import com.uflow.uflow.domain.utils.TaskOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTasksUseCase(
    private val repository: TaskRepository
) {
    operator fun invoke(
        taskOrder: TaskOrder
    ): Flow<List<Task>>{
        return repository.getTasks().map { task ->
            when(taskOrder){
                is TaskOrder.Category -> task.sortedByDescending {
                    it.category.lowercase()
                }
                TaskOrder.Completes -> task.sortedByDescending {
                    it.isComplete
                }
                TaskOrder.Date -> task.sortedByDescending {
                    it.date
                }
                is TaskOrder.Priority -> task.sortedByDescending {
                    it.priority
                }

                TaskOrder.NoCompletes -> task.sortedBy {
                    it.isComplete
                }
            }
        }
    }
}