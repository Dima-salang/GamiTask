package com.example.gamitask.features.taskmanagement.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamitask.features.taskmanagement.data.dao.TaskDAO
import com.example.gamitask.features.taskmanagement.data.models.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private val taskDAO: TaskDAO) : ViewModel() {
    // Private mutable state flow to store the task list
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())

    // Public immutable state flow exposed to the UI
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    // Function to add a new task
    fun insertTask(task: Task) {
        viewModelScope.launch {
            val currentList = _tasks.value.toMutableList()
            currentList.add(task)
            _tasks.value = currentList
        }
    }

    // Function to delete a task
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            val currentList = _tasks.value.toMutableList()
            currentList.remove(task)
            _tasks.value = currentList
        }
    }

    // Function to update task completion status
    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            val currentList = _tasks.value.toMutableList()
            val index = currentList.indexOfFirst { it.id == task.id }
            if (index != -1) {
                // Create a copy of the task with toggled completion state
                val updatedTask = task.copy(isCompleted = !task.isCompleted)
                currentList[index] = updatedTask
                _tasks.value = currentList
            }
        }
    }
}