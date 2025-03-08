package com.example.taskManager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskManager.model.TaskEntity
import com.example.taskManager.networks.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    var title = MutableStateFlow("")
        private set
    var description = MutableStateFlow("")
        private set
    var priority = MutableStateFlow("")
        private set
    var dueDate = MutableStateFlow(Date()) // Default: Current Date
        private set

    fun setTitle(value: String) {
        title.value = value
    }

    fun setDescription(value: String) {
        description.value = value
    }

    fun setPriority(value: String) {
        priority.value = value
    }

    fun setDueDate(value: Date) {
        dueDate.value = value
    }

    fun insertTask(onSuccess: () -> Unit) {
        viewModelScope.launch {
            if (title.value.isNotBlank() && priority.value.isNotBlank()) {
                val task = TaskEntity(
                    title = title.value,
                    description = description.value.takeIf { it.isNotBlank() },
                    priority = priority.value,
                    dueDate = dueDate.value,
                    status = "Pending"
                )
                repository.insertTask(task)
                onSuccess()
            }
        }
    }
}
