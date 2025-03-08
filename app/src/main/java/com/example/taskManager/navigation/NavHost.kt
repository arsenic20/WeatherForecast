package com.example.taskManager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taskManager.model.Screen
import com.example.taskManager.screens.CreateTaskScreen
import com.example.taskManager.screens.TaskDetailScreen
import com.example.taskManager.screens.TasksScreen
import com.example.taskManager.utils.deserializeTaskBase64
import com.example.taskManager.utils.serializeTaskBase64

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Tasks.route) {
        composable(Screen.Tasks.route) {
            TasksScreen(
                onNavigateToDetails = { task ->
                    val serializedTask = serializeTaskBase64(task)
                    navController.navigate(Screen.TaskDetails.createRoute(serializedTask))
                },
                onNavigateToCreateTask = {
                    navController.navigate(Screen.CreateTask.route)
                }
            )
        }

        composable(Screen.CreateTask.route) {
            CreateTaskScreen(
                onCancel = { navController.popBackStack() },
                onCreateTask = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.TaskDetails.route,
            arguments = listOf(navArgument("task") { type = NavType.StringType })
        ) { backStackEntry ->
            val serializedTask = backStackEntry.arguments?.getString("task") ?: ""
            val task = deserializeTaskBase64(serializedTask)

            TaskDetailScreen(
                task = task,
                onTaskComplete = { navController.popBackStack() },
                onTaskDeleted = { navController.popBackStack() }
            )
        }
    }
}


