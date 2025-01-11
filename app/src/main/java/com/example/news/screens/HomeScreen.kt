package com.example.news.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.news.roomDb.SavedArticles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDetails: (SavedArticles) -> Unit,
    selectedTab: Int,
    onSelectedTab: (Int) -> Unit,
    toolBarText: String
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = toolBarText,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarColors(Color.Gray, Color.Gray, Color.Gray, Color.Gray, Color.Gray)
            )
        },
        bottomBar = {
            BottomNavigation(backgroundColor = Color.Gray) {
                BottomNavigationItem(
                    icon = {
                        Icon(
                            if (selectedTab == 0) Icons.Filled.Home else Icons.Outlined.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = { Text("Home") },
                    selected = selectedTab == 0,
                    onClick = { onSelectedTab(0) }

                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            if (selectedTab == 1) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Saved"
                        )
                    },
                    label = { Text("Saved") },
                    selected = selectedTab == 1,
                    onClick = { onSelectedTab(1) }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                0 -> BlogScreen(onNavigateToDetails, selectedTab)
                1 -> SavedBlogScreen(onNavigateToDetails, selectedTab)
            }
        }
    }
}
