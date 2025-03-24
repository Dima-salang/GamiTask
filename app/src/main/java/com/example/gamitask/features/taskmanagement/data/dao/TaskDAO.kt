package com.example.gamitask.features.taskmanagement.data.dao
import androidx.room.*
import com.example.gamitask.features.taskmanagement.domain.models.Task

@Dao
interface TaskDAO {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): List<Task>

    @Insert
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)
}