package com.example.gamitask.features.taskmanagement.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gamitask.features.taskmanagement.data.models.Task
import com.example.gamitask.features.taskmanagement.data.viewmodel.TaskViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoApplication(taskViewModel: TaskViewModel = hiltViewModel()) {
    val taskList by taskViewModel.tasks.collectAsStateWithLifecycle()
    var taskText by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "GamiTask") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White,
                )
            )
        },
        bottomBar = {
            BottomInputBar(
                taskText = taskText,
                onTaskTextChange = { taskText = it },
                onAddTask = {
                    if (taskText.isNotBlank()) {
                        val newTask = Task(description = taskText)
                        taskViewModel.insertTask(newTask)
                        taskText = ""  // Clear input
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            TaskList(
                tasks = taskList,
                onDelete = { taskViewModel.deleteTask(it) }
            )
        }
    }
}


@Composable
fun BottomInputBar(taskText: String, onTaskTextChange: (String) -> Unit, onAddTask: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = taskText,
            onValueChange = onTaskTextChange,
            label = { Text("New Task") },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = onAddTask,
            modifier = Modifier
                .height(56.dp)  // Match input field height
        ) {
            Text("Add")
        }
    }
}

@Composable
fun TaskList(tasks: List<Task>, onDelete: (Task) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 64.dp)  // Space for input field
    ) {
        items(tasks) { task ->
            TaskItem(task = task, onDelete = onDelete)
        }
    }
}

@Composable
fun TaskItem(task: Task, modifier: Modifier = Modifier, onDelete: (Task) -> Unit) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = task.description, modifier = Modifier.weight(1f))
            Button(onClick = { onDelete(task) }) {
                Text("Delete")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToDoApplicationPreview() {
    ToDoApplication()
}
