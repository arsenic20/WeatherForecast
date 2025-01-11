package com.example.news.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.news.roomDb.SavedArticles


@Composable
fun NewsCard(
    article: SavedArticles,
    onNavigateToDetails: (SavedArticles) -> Unit,
    index: Int,
    selectedTab: Int,
    onDeleteClick: (SavedArticles) -> Unit // Callback for delete action
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = article.urlToImage,
                        contentDescription = "News Image",
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .size(64.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Gray),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = article.author,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 20.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                        Text(
                            text = article.description,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Read More...",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable {
                            if (article.id == 0)
                                onNavigateToDetails(article.copy(id = index.plus(1)))
                            else onNavigateToDetails(article)
                        }
                )
            }
        }
        if (selectedTab == 1)
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Article",
                tint = Color.Black,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(20.dp)
                    .clickable { onDeleteClick(article) }
            )
    }
}
