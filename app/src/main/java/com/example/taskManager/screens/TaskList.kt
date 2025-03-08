package com.example.taskManager.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskManager.R
import com.example.taskManager.model.TaskEntity
import com.example.taskManager.viewModel.TasksScreenViewModel

@Composable
fun TaskList(onNavigateToDetails: (TaskEntity) -> Unit) {
    val viewModel = hiltViewModel<TasksScreenViewModel>()
    val taskData: State<List<TaskEntity>?> = viewModel.tasks.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (taskData.value?.isEmpty() == true) {
            // Show "No Blogs Saved" message
            Text(
                text = stringResource(R.string.no_task_found),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 30.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            taskData.value?.let { data ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    itemsIndexed(data) { index, task ->
                        TaskItem(
                            task = task,
                            onNavigateToDetails = onNavigateToDetails,
                            index = index
                        )
                    }
                }
            } ?: run {
                // Optional: Handle the case where there's no data
                Text(text = viewModel.errorMessage.value ?: stringResource(R.string.no_task_found),
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 30.sp,
                    modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}