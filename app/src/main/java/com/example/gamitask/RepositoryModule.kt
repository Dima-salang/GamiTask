package com.example.gamitask

import android.content.Context
import androidx.room.Room
import com.example.gamitask.features.taskmanagement.data.dao.TaskDAO
import com.example.gamitask.features.taskmanagement.data.db.TaskDatabase
import com.example.gamitask.features.taskmanagement.data.repositories.TaskRepository
import dagger.hilt.InstallIn
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideTaskRepository(taskDAO: TaskDAO): TaskRepository {
        return TaskRepository(taskDAO)
    }

    @Provides
    @Singleton
    fun provideTaskDAO(taskDatabase: TaskDatabase): TaskDAO {
        return taskDatabase.taskDao()
    }

    @Provides
    @Singleton
    fun provideTaskDatabase(@ApplicationContext context: Context): TaskDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            TaskDatabase::class.java,
            "task_database"
        ).build()
    }
}