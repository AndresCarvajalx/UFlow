package com.uflow.uflow.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.uflow.uflow.data.database.dao.TaskDao
import com.uflow.uflow.data.database.dao.WorkTaskDao
import com.uflow.uflow.domain.models.Task
import com.uflow.uflow.domain.models.WorkTask
import com.uflow.uflow.domain.utils.LocalDateConverter
import com.uflow.uflow.domain.utils.LocalTimeConverter

@Database(entities = [Task::class, WorkTask::class], version = 9)
@TypeConverters(LocalDateConverter::class, LocalTimeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract val taskDao: TaskDao
    abstract val workTaskDao: WorkTaskDao
}