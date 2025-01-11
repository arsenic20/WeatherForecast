package com.example.news.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.news.R
import com.example.news.screens.DetailsScreen
import com.example.news.screens.HomeScreen
import com.example.news.utils.deserializeArticleBase64
import com.example.news.utils.serializeArticleBase64

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var selectedTab by remember { mutableIntStateOf(0) }
    val saved = stringResource(R.string.saved_blogs)
    val home = stringResource(R.string.blogs)
    var toolBarText by remember { mutableStateOf(home) }


    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateToDetails = { article ->
                    val serializedArticle = serializeArticleBase64(article)
                    navController.navigate("details?article=$serializedArticle")
                },
                selectedTab,
                onSelectedTab = {
                    toolBarText = if(it==0) home
                    else saved
                    selectedTab = it
                },
                toolBarText
            )
        }
        composable(
            route = "details?article={article}",
            arguments = listOf(navArgument("article") { type = NavType.StringType })
        ) { backStackEntry ->
            val serializedArticle = backStackEntry.arguments?.getString("article") ?: ""
            val article = deserializeArticleBase64(serializedArticle)

            DetailsScreen(
                article = article,
                onSave = {
                     selectedTab = 1
                     toolBarText = saved
                     navController.popBackStack() /* Handle save action */ }
            )
        }

    }
}


