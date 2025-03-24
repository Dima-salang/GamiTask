package com.example.gamitask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gamitask.features.taskmanagement.data.viewmodel.TaskViewModel
import com.example.gamitask.features.taskmanagement.presentation.components.ToDoApplication
import com.example.gamitask.ui.theme.GamiTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GamiTaskTheme {
                ToDoApplication()
            }
        }
    }
}