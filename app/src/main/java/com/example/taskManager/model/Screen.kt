package com.example.taskManager.model

sealed class Screen(val route: String) {
    data object Tasks : Screen("tasks")
    data object CreateTask : Screen("createTask")
    data object TaskDetails : Screen("taskDetails?task={task}") {
        fun createRoute(task: String) = "taskDetails?task=$task"
    }
}