package com.example.taskManager.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.taskManager.model.TaskEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    onNavigateToDetails: (TaskEntity) -> Unit,
    onNavigateToCreateTask: () -> Unit
) {
    var showFilterPopup by remember { mutableStateOf(false) }
    var showSortPopup by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tasks", fontSize = 20.sp) },
                actions = {
                    Row {
                        IconButton(onClick = { showFilterPopup = true }) {
                            Icon(Icons.Default.Face, contentDescription = "Filter Tasks")
                        }
                        IconButton(onClick = { showSortPopup = true }) {
                            Icon(Icons.Default.Star, contentDescription = "Sort Tasks")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreateTask) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            TaskList(onNavigateToDetails)
        }
    }

    // Popup Dialog for Filter
    if (showFilterPopup) {
        FilterPopup(onDismiss = { showFilterPopup = false })
    }

    // Popup Dialog for Sort
    if (showSortPopup) {
        SortPopup(onDismiss = { showSortPopup = false })
    }
}