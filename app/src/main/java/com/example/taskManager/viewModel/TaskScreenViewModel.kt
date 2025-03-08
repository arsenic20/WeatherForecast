package com.example.taskManager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskManager.model.TaskEntity
import com.example.taskManager.networks.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksScreenViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<TaskEntity>?>(emptyList())
    val tasks: StateFlow<List<TaskEntity>?> = _tasks.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _filter = MutableStateFlow("All")
    private val _sortOrder = MutableStateFlow("Due Date (Newest)")

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _isLoading.emit(true)
            _errorMessage.emit(null) // Reset error message

            try {
                repository.getAllTasks()
                    .collectLatest { fetchedTasks ->
                        _tasks.value = applyFilterAndSort(fetchedTasks)
                        _isLoading.emit(false)
                    }
            } catch (e: Exception){
                _errorMessage.emit("Failed to fetch task: ${e.localizedMessage}")

            } finally {
                _isLoading.emit(false)
            }

        }
    }

    fun setFilter(filter: String) {
        _filter.value = filter
        applyFilterAndSort()
    }

    fun setSortOrder(sortOrder: String) {
        _sortOrder.value = sortOrder
        applyFilterAndSort()
    }

    private fun applyFilterAndSort(tasksList: List<TaskEntity>? = _tasks.value): List<TaskEntity>? {
        val filteredTasks = when (_filter.value) {
            "Pending" -> tasksList?.filter { it.status == "Pending" }
            "Completed" -> tasksList?.filter { it.status == "Completed" }
            else -> tasksList
        }

        val sortedTasks = when (_sortOrder.value) {
            "Due Date (Newest)" -> filteredTasks?.sortedByDescending { it.dueDate }
            "Due Date (Oldest)" -> filteredTasks?.sortedBy { it.dueDate }
            "Priority (High-Low)" -> filteredTasks?.sortedByDescending { it.priority }
            "Priority (Low-High)" -> filteredTasks?.sortedBy { it.priority }
            else -> filteredTasks
        }

        _tasks.value = sortedTasks
        return sortedTasks
    }
}
