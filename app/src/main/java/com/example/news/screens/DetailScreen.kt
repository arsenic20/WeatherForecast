package com.example.news.screens

import android.annotation.SuppressLint
import android.webkit.WebView
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.news.R
import com.example.news.roomDb.SavedArticles
import com.example.news.viewModel.SaveScreenViewModel

@SuppressLint("SetJavaScriptEnabled", "StateFlowValueCalledInComposition")
@Composable
fun DetailsScreen(
    article: SavedArticles, // URL for the WebView
    onSave: () -> Unit, // Action to perform when the Save button is clicked
) {
    val viewModel = hiltViewModel<SaveScreenViewModel>()
    val savedNewsData: State<List<SavedArticles?>> = viewModel.savedBlogs.collectAsState()
    val context = LocalContext.current
    val entryAddedMessage = stringResource(R.string.saving_data)
    val duplicateEntryMessage = stringResource(R.string.data_already_added)

    Box(modifier = Modifier.fillMaxSize()) {
        // WebView Section
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    loadUrl(article.url)
                }
            }
        )

        // FloatingActionButton
        FloatingActionButton(
            onClick = {
                if (savedNewsData.value.isEmpty() || (savedNewsData.value.find { it?.id == article.id } == null)) {
                    Toast.makeText(context, entryAddedMessage, Toast.LENGTH_SHORT).show()
                    viewModel.addEntity(article)
                    onSave()
                } else {
                    Toast.makeText(context, duplicateEntryMessage, Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 64.dp, end = 16.dp),
            containerColor = Color.Gray
        ) {
            Text(text = stringResource(R.string.save), color = Color.Black)
        }
    }
}
