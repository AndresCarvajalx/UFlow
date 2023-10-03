package com.uflow.uflow.data.repository

import com.uflow.uflow.data.database.dao.WorkTaskDao
import com.uflow.uflow.domain.models.WorkTask
import com.uflow.uflow.domain.respository.WorkTaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class WorkTaskRepositoryImpl (
    private val workTaskDao: WorkTaskDao
): WorkTaskRepository {

    override fun getWorkTasks(): Flow<List<WorkTask>> {
        return flow<List<WorkTask>> {
            workTaskDao.getWorkTasks().map {
                emit(it)
            }.collect()
        }
    }

    override suspend fun getWorkTaskById(id: Int): WorkTask? {
        return workTaskDao.getWorkTaskById(id)
    }

    override suspend fun insertWorkTask(workTask: WorkTask) {
        workTaskDao.insertWorkTask(workTask)
    }

    override suspend fun updateWorkTask(workTask: WorkTask) {
        workTaskDao.updateWorkTask(workTask)
    }

    override suspend fun deleteWorkTask(workTask: WorkTask) {
        workTaskDao.deleteWorkTask(workTask)
    }

}