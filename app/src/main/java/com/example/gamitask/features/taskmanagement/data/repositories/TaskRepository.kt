package com.example.gamitask.features.taskmanagement.data.repositories
import com.example.gamitask.features.taskmanagement.data.dao.TaskDAO
import com.example.gamitask.features.taskmanagement.data.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TaskRepository(private val taskDao: TaskDAO) {
    fun getAllTasks(): Flow<List<Task>> = flow {
        emit(taskDao.getAllTasks())
    }
    suspend fun insertTask(task: Task) = taskDao.insertTask(task)
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)


}