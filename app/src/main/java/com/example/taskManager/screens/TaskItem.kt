package com.example.taskManager.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskManager.model.TaskEntity


@Composable
fun TaskItem(
    task: TaskEntity,
    onNavigateToDetails: (TaskEntity) -> Unit,
    index: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = { onNavigateToDetails(task) }),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = task.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = task.description ?: "No description", fontSize = 14.sp, color = Color.Gray)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Due: ${task.dueDate}", fontSize = 12.sp, color = Color.Red)
                Text(text = "Priority: ${task.priority}", fontSize = 12.sp, color = Color.Blue)
                Text(text = "Status: ${task.status}", fontSize = 12.sp, color = Color.Green)
            }
        }
    }
}
