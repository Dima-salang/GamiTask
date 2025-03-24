package com.example.gamitask.features.taskmanagement.data.models
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity (tableName = "tasks")
// data class for task
data class Task(
    @PrimaryKey (autoGenerate = true) var id: Int=0,
    val description: String,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
