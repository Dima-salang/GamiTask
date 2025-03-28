package com.example.gamitask.features.taskmanagement.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamitask.features.taskmanagement.data.dao.TaskDAO
import com.example.gamitask.features.taskmanagement.data.models.Task
import com.example.gamitask.features.taskmanagement.data.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    // Private mutable state flow to store the task list
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    private val _completedTasks = MutableStateFlow<List<Task>>(emptyList())

    // Public immutable state flow exposed to the UI
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()
    val completedTasks: StateFlow<List<Task>> = _completedTasks.asStateFlow()

    init {
        viewModelScope.launch {
            taskRepository.getAllTasks().collect { tasks ->
                _tasks.value = tasks.filter { !it.isCompleted }
                _completedTasks.value = tasks.filter { it.isCompleted }
            }
        }
    }

    // Function to add a new task
    fun insertTask(task: Task) {
        viewModelScope.launch {
            taskRepository.insertTask(task)
        }
    }

    // Function to delete a task
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }

    // Function to update task completion status
    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy(isCompleted = !task.isCompleted)
            taskRepository.updateTask(updatedTask)
        }
    }
}