package com.uflow.uflow.domain.respository

import com.uflow.uflow.domain.models.WorkTask
import kotlinx.coroutines.flow.Flow

interface WorkTaskRepository{
    fun getWorkTasks(): Flow<List<WorkTask>>
    suspend fun getWorkTaskById(id: Int): WorkTask?
    suspend fun insertWorkTask(workTask: WorkTask)
    suspend fun updateWorkTask(workTask: WorkTask)
    suspend fun deleteWorkTask(workTask: WorkTask)
}