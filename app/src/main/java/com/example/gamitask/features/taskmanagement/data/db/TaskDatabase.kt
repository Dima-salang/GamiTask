package com.example.gamitask.features.taskmanagement.data.db
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gamitask.features.taskmanagement.data.models.Task
import com.example.gamitask.features.taskmanagement.data.dao.TaskDAO
import android.content.Context

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDAO

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null
        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            TaskDatabase::class.java,
            "task_database"
        ).build()
    }

}