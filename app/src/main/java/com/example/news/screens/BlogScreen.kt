package com.example.news.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.news.R
import com.example.news.model.NewsData
import com.example.news.roomDb.SavedArticles
import com.example.news.viewModel.HomeScreenViewModel

@Composable
fun BlogScreen(onNavigateToDetails: (SavedArticles) -> Unit, selectedTab: Int) {
    val viewModel = hiltViewModel<HomeScreenViewModel>()
    val newsData: State<NewsData?> = viewModel.newsData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = Color.Gray)
        } else {
            newsData.value?.articles?.let { data ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    itemsIndexed(data) { index, article ->
                        NewsCard(
                            article = article,
                            onNavigateToDetails = onNavigateToDetails,
                            index = index,
                            selectedTab,
                            onDeleteClick = {

                            }
                        )
                    }
                }
            } ?: run {
                // Optional: Handle the case where there's no data
                Text(text = viewModel.errorMessage.value ?: stringResource(R.string.no_blogs_found),
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 30.sp,
                    modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}