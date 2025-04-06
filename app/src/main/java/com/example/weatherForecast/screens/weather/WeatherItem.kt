package com.example.weatherForecast.screens.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.weatherForecast.local.WeatherEntity
import com.example.weatherForecast.utils.kelvinToCelsius
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun WeatherForecastCard(forecast: WeatherEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.8f),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFADD8E6), // Light Blue
                            Color(0xFFD3D3D3)  // Dark Blue
                        )
                    )
                )
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Top Section - City and Date/Time
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = forecast.city,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = formatDate(forecast.date),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Text(
                    text = forecast.time,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            // Middle Section - Weather Icon
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = "https://openweathermap.org/img/wn/${forecast.icon}@2x.png",
                    contentDescription = forecast.description,
                    modifier = Modifier.size(80.dp)
                )
            }

            // Bottom Section - Temperature and Description
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${kelvinToCelsius(forecast.temperature).toInt()}°C",
                    style = MaterialTheme.typography.displaySmall
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = forecast.description.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

private fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        val outputFormat = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
        outputFormat.format(date ?: dateString)
    } catch (e: Exception) {
        dateString
    }
}