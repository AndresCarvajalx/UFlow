package com.uflow.uflow.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uflow.uflow.domain.models.WorkTask
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkTaskDao {
    @Query("SELECT * FROM work_task")
    fun getWorkTasks(): Flow<List<WorkTask>>

    @Query("SELECT * FROM work_task WHERE id = :id")
    suspend fun getWorkTaskById(id: Int): WorkTask?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkTask(workTask: WorkTask)

    @Update
    suspend fun updateWorkTask(workTask: WorkTask)

    @Delete
    suspend fun deleteWorkTask(workTask: WorkTask)
}