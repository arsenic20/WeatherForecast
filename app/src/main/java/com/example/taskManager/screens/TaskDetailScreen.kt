package com.example.taskManager.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskManager.model.TaskEntity
import com.example.taskManager.viewModel.TaskDetailViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    task: TaskEntity,
    onTaskComplete: () -> Unit,
    onTaskDeleted: () -> Unit
) {
   val viewModel: TaskDetailViewModel = hiltViewModel()
    val currentTask by viewModel.task.collectAsState()

    LaunchedEffect(task) {
        viewModel.setTask(task)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Task Details") }) }
    ) { paddingValues ->
        currentTask?.let { task ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Big Card with Task Details
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = task.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = task.description ?: "No Description",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Status Badge (Pending = Red, Completed = Green)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Status: ${task.status}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (task.status == "Pending") Color.Red else Color.Green,
                                modifier = Modifier
                                    .background(
                                        color = if (task.status == "Pending") Color(0xFFFFCDD2) else Color(0xFFC8E6C9),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(8.dp)
                            )

                            Text(
                                text = "Due: ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(task.dueDate)}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f)) // Pushes buttons to bottom

                // Action Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Complete Button (Disabled if already completed)
                    Button(
                        onClick = { viewModel.markAsComplete(onTaskComplete) },
                        enabled = task.status != "Completed"
                    ) {
                        Text("Complete")
                    }

                    // Delete Button
                    Button(
                        onClick = { viewModel.deleteTask(onTaskDeleted) },
                        colors = ButtonDefaults.buttonColors(Color.Red)
                    ) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}
