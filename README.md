# Weather Forecast App 🌤️

<img src="https://img.shields.io/badge/Kotlin-1.9.23-blue" alt="Kotlin"> 
<img src="https://img.shields.io/badge/Jetpack%20Compose-1.5.0-red" alt="Jetpack Compose">
<img src="https://img.shields.io/badge/OpenWeatherMap-API-green" alt="OpenWeatherMap">

A modern Android application that displays 3-day weather forecasts with seamless offline support, built entirely with Jetpack Compose and modern Android architecture components.

## ✨ Key Features
- **Interactive Forecast Grid**: Visually appealing card layout showing date, temperature, and weather conditions
- **Intelligent City Search**: Real-time search with error handling and suggestions
- **Offline-First Design**: Automatic local caching of weather data
- **Material 3 UI**: Adaptive theming with dynamic colors
- **Data Refresh**: Manual refresh capability with visual feedback

## 🛠️ Technical Stack
| Component           | Technology Used          |
|---------------------|--------------------------|
| UI Framework        | Jetpack Compose          |
| Dependency Injection| Dagger Hilt              |
| Networking          | Retrofit + Moshi         |
| Local Database      | Room DB                  |
| Image Loading       | Coil                     |
| Coroutines + Flow   |

## 📊 Architecture Overview
### Data Flow Pipeline
1. **UI Layer**: Jetpack Compose screens observing ViewModel state changes
2. **ViewModel**: Business logic handler with state persistence
3. **Repository**: Mediates between network and local data sources
4. **Data Sources**:
   - **OpenWeatherMap API**: Provides real-time weather data
   - **Room Database**: Persistent cache for offline access

### Offline Support Mechanism
- Automatic data caching on successful API responses
- Smart fallback to cached data during network failures
- Timestamp-based cache validation

## 🔄 Core Functionality
1. **Weather Data Retrieval**:
   - Fetches 3-day forecasts from OpenWeatherMap API
   - Transforms API responses into domain models
   - Handles error states gracefully

2. **User Interface**:
   - Dynamic search bar with type-ahead suggestions
   - Responsive grid layout for forecasts
   - Themed weather condition icons
   - Progress and error indicators

3. **Data Persistence**:
   - Room database schema optimized for forecast data
   - Automatic data synchronization
   - Lifecycle-aware data loading
