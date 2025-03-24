package com.example.gamitask.features.taskmanagement.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gamitask.features.taskmanagement.data.models.Task
import com.example.gamitask.features.taskmanagement.data.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoApplication(taskViewModel: TaskViewModel = hiltViewModel()) {
    val taskList by taskViewModel.tasks.collectAsStateWithLifecycle()
    val completedTasks by taskViewModel.completedTasks.collectAsStateWithLifecycle()
    var showInputField by remember { mutableStateOf(false) }
    var taskText by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(text = "GamiTask", style = MaterialTheme.typography.headlineSmall)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showInputField = !showInputField }, // Toggle input field visibility
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task")
            }
        },
        bottomBar = {
            if (showInputField) {
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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            TaskContainer(
                tasks = taskList,
                onDelete = { taskViewModel.deleteTask(it) },
                onToggleComplete = { taskViewModel.toggleTaskCompletion(it) }
            )

            CompletedTaskContainer(
                completedTasks = completedTasks,
                onDelete = { taskViewModel.deleteTask(it) },
                onToggleComplete = { taskViewModel.toggleTaskCompletion(it) }
            )
        }
    }
}


@Composable
fun TaskContainer(
    tasks: List<Task>,
    onDelete: (Task) -> Unit,
    onToggleComplete: (Task) -> Unit
) {
    var expanded by remember { mutableStateOf(true) } // Controls dropdown state

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            // Header with Title and Expand Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tasks",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f) // Pushes button to the right
                )

                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (expanded) "Collapse" else "Expand"
                    )
                }
            }

            // Task List (Shown only when expanded)
            if (expanded) {
                TaskList(
                    tasks = tasks,
                    onDelete = onDelete,
                    onToggleComplete = onToggleComplete
                )
            }
        }
    }
}



@Composable
fun CompletedTaskContainer(
    completedTasks: List<Task>,
    onDelete: (Task) -> Unit,
    onToggleComplete: (Task) -> Unit
) {
    var expanded by remember { mutableStateOf(false) } // Collapsible state

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            // Header with Title and Expand Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Completed Tasks",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (expanded) "Collapse" else "Expand"
                    )
                }
            }

            // Completed Task List (Only visible when expanded)
            if (expanded) {
                TaskList(
                    tasks = completedTasks,
                    onDelete = onDelete,
                    onToggleComplete = onToggleComplete
                )
            }
        }
    }
}



@Composable
fun BottomInputBar(taskText: String, onTaskTextChange: (String) -> Unit, onAddTask: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(24.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .windowInsetsPadding(WindowInsets.navigationBars) // Prevent overlapping navigation buttons
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = taskText,
                onValueChange = onTaskTextChange,
                placeholder = { Text("Add a task...") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(24.dp))
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = onAddTask,
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task", tint = Color.White)
            }
        }
    }
}


@Composable
fun TaskList(tasks: List<Task>, onDelete: (Task) -> Unit, onToggleComplete: (Task) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(tasks) { task ->
                TaskItem(task = task, onDelete = onDelete, onToggleComplete = onToggleComplete)
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onDelete: (Task) -> Unit, onToggleComplete: (Task) -> Unit) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart || dismissValue == SwipeToDismissBoxValue.StartToEnd) {
                onDelete(task) // Call delete function
                true // Confirm dismissal
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val color = when (dismissState.targetValue) {
                SwipeToDismissBoxValue.EndToStart, SwipeToDismissBoxValue.StartToEnd -> Color.Red
                else -> MaterialTheme.colorScheme.surfaceVariant
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }
        },
        content = {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                ListItem(
                    headlineContent = { Text(task.description) },
                    leadingContent = {
                        Checkbox(
                            checked = task.isCompleted,
                            onCheckedChange = { onToggleComplete(task) }
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        enableDismissFromEndToStart = true,
        enableDismissFromStartToEnd = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun ToDoApplicationPreview() {
    ToDoApplication()
}
