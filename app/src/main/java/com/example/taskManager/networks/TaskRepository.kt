package com.example.taskManager.networks

import com.example.taskManager.model.TaskEntity
import com.example.taskManager.roomDb.TaskDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(private val dao: TaskDao) {

    fun getAllTasks(): Flow<List<TaskEntity>> = dao.getAllTasks()

    fun getTasksSortedByName(): Flow<List<TaskEntity>> = dao.getTasksSortedByName()

    fun getTasksSortedByNewestDate(): Flow<List<TaskEntity>> = dao.getTasksSortedByNewestDate()

    fun getTasksSortedByOldestDate(): Flow<List<TaskEntity>> = dao.getTasksSortedByOldestDate()

    fun getTasksSortedByPriorityHighToLow(): Flow<List<TaskEntity>> = dao.getTasksSortedByPriorityHighToLow()

    fun getTasksSortedByPriorityLowToHigh(): Flow<List<TaskEntity>> = dao.getTasksSortedByPriorityLowToHigh()

    suspend fun insertTask(task: TaskEntity) {
        dao.insertTask(task)
    }

    suspend fun deleteTask(task: TaskEntity) {
        dao.deleteTask(task)
    }

    suspend fun updateTaskStatus(taskId: Int, status: String) {
        dao.updateTaskStatus(taskId, status)
    }
}


