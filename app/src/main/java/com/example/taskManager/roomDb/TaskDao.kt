package com.example.taskManager.roomDb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskManager.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("UPDATE tasks SET status = :status WHERE id = :taskId")
    suspend fun updateTaskStatus(taskId: Int, status: String)

    @Query("SELECT * FROM tasks ORDER BY title ASC")
    fun getTasksSortedByName(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks ORDER BY dueDate DESC")
    fun getTasksSortedByNewestDate(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks ORDER BY dueDate ASC")
    fun getTasksSortedByOldestDate(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks ORDER BY priority DESC")
    fun getTasksSortedByPriorityHighToLow(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks ORDER BY priority ASC")
    fun getTasksSortedByPriorityLowToHigh(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>

}
