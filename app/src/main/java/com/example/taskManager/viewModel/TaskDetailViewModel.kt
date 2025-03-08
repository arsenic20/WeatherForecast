package com.example.taskManager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskManager.model.TaskEntity
import com.example.taskManager.networks.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _task = MutableStateFlow<TaskEntity?>(null)
    val task: StateFlow<TaskEntity?> = _task.asStateFlow()

    fun setTask(task: TaskEntity) {
        _task.value = task
    }

    fun markAsComplete(onSuccess: () -> Unit) {
        _task.value?.let { currentTask ->
            if (currentTask.status != "Completed") {
                val updatedTask = currentTask.copy(status = "Completed")
                viewModelScope.launch {
                    repository.updateTaskStatus(updatedTask.id, "Completed")
                    _task.value = updatedTask
                    onSuccess()
                }
            }
        }
    }

    fun deleteTask(onSuccess: () -> Unit) {
        _task.value?.let { taskToDelete ->
            viewModelScope.launch {
                repository.deleteTask(taskToDelete)
                onSuccess()
            }
        }
    }
}
