package com.uflow.uflow.di

import android.app.Application
import androidx.room.Room
import com.uflow.uflow.data.database.AppDatabase
import com.uflow.uflow.data.repository.TaskRepositoryImpl
import com.uflow.uflow.data.repository.WorkTaskRepositoryImpl
import com.uflow.uflow.domain.respository.TaskRepository
import com.uflow.uflow.domain.respository.WorkTaskRepository
import com.uflow.uflow.domain.use_case.task_use_cases.AddTaskUseCase
import com.uflow.uflow.domain.use_case.task_use_cases.DeleteTaskUseCase
import com.uflow.uflow.domain.use_case.task_use_cases.GetTaskUseCase
import com.uflow.uflow.domain.use_case.task_use_cases.GetTasksUseCase
import com.uflow.uflow.domain.use_case.task_use_cases.TaskUseCases
import com.uflow.uflow.domain.use_case.task_use_cases.UpdateTaskUseCase
import com.uflow.uflow.domain.use_case.work_task_use_cases.AddWorkTaskUseCase
import com.uflow.uflow.domain.use_case.work_task_use_cases.DeleteWorkTaskUseCase
import com.uflow.uflow.domain.use_case.work_task_use_cases.GetWorkTaskByIdUseCase
import com.uflow.uflow.domain.use_case.work_task_use_cases.GetWorkTasksUseCase
import com.uflow.uflow.domain.use_case.work_task_use_cases.UpdateWorkTaskUseCase
import com.uflow.uflow.domain.use_case.work_task_use_cases.WorkTaskUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "uflow_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesTaskRepository(db: AppDatabase): TaskRepository {
        return TaskRepositoryImpl(db.taskDao)
    }

    @Provides
    @Singleton
    fun providesTaskUseCases(repository: TaskRepository): TaskUseCases {
        return TaskUseCases(
            addTaskUseCase = AddTaskUseCase(repository),
            deleteTaskUseCase = DeleteTaskUseCase(repository),
            getTasksUseCase = GetTasksUseCase(repository),
            getTaskUseCase = GetTaskUseCase(repository),
            updateTaskUseCase = UpdateTaskUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun providesWorkTaskRepository(db: AppDatabase): WorkTaskRepository {
        return WorkTaskRepositoryImpl(db.workTaskDao)
    }

    @Provides
    @Singleton
    fun providesWorkTaskUseCases(repository: WorkTaskRepository): WorkTaskUseCases {
        return WorkTaskUseCases(
            addWorkTaskUseCase = AddWorkTaskUseCase(repository),
            deleteWorkTaskUseCase = DeleteWorkTaskUseCase(repository),
            getWorkTasksUseCase = GetWorkTasksUseCase(repository),
            getWorkTaskByIdUseCase = GetWorkTaskByIdUseCase(repository),
            updateWorkTaskUseCase = UpdateWorkTaskUseCase(repository)
        )
    }
}