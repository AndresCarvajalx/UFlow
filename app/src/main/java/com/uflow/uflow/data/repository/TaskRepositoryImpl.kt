package com.uflow.uflow.data.repository

import com.uflow.uflow.data.database.dao.TaskDao
import com.uflow.uflow.domain.models.Task
import com.uflow.uflow.domain.respository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : TaskRepository {
    override fun getTasks(): Flow<List<Task>> {
        return flow<List<Task>> {
            taskDao.getTasks().map {
                emit(it)
            }.collect()
        }
    }

    override suspend fun getTaskById(id: Int): Task? {
        return taskDao.getTaskById(id)
    }

    override suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            taskDao.deleteTask(task)
        }
    }
}