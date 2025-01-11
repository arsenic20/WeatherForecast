package com.example.news.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.news.R
import com.example.news.roomDb.SavedArticles
import com.example.news.viewModel.SaveScreenViewModel


@Composable
fun SavedBlogScreen(onNavigateToDetails: (SavedArticles) -> Unit, selectedTab: Int) {
    val viewModel = hiltViewModel<SaveScreenViewModel>()
    val savedNewsData: State<List<SavedArticles?>> = viewModel.savedBlogs.collectAsState()
    viewModel.refreshData()

    Box(modifier = Modifier.fillMaxSize()) {
        if (savedNewsData.value.isEmpty()) {
            // Show "No Blogs Saved" message
            Text(
                text = stringResource(R.string.no_blog_saved),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 30.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            // Show the list of saved blogs
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                itemsIndexed(savedNewsData.value) { index, article ->
                    if (article != null) {
                        NewsCard(
                            article = article,
                            onNavigateToDetails = onNavigateToDetails,
                            index = index,
                            selectedTab = selectedTab,
                            onDeleteClick = {
                                viewModel.deleteEntity(article)
                                // Handle delete action here
                            }
                        )
                    }
                }
            }
        }
    }
}

