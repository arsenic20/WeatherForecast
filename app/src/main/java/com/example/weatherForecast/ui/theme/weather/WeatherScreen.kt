package com.example.weatherForecast.ui.theme.weather

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherForecast.local.WeatherEntity
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.grid.items

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreen(
) {
    val viewModel:WeatherViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { viewModel.updateSearchQuery(it) },
            onSearch = { viewModel.fetchWeather() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = uiState) {
            is WeatherUiState.Empty -> {
                Text(
                    text = "Enter a city name to get weather forecast",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            is WeatherUiState.Loading -> {
                CircularProgressIndicator()
            }
            is WeatherUiState.Success -> {
                WeatherForecastList(forecasts = state.forecasts)
            }
            is WeatherUiState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            label = { Text("City name") },
            modifier = Modifier.weight(1f),
            singleLine = true
        )

        Spacer(modifier = Modifier.width(8.dp))

        Button(onClick = onSearch) {
            Text("Search")
        }
    }
}

@Composable
fun WeatherForecastList(forecasts: List<WeatherEntity>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp), // Minimum item width
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(forecasts) { forecast ->
            WeatherForecastCard(forecast = forecast)
        }
    }
}
