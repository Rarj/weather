package dev.arj.cuacanusantara.data.weather.source

import dev.arj.cuacanusantara.network.ApiService

class WeatherRemoteDataSource(private val apiService: ApiService) {
    suspend fun fetchCurrentWeather(latitude: String, longitude: String) =
        apiService.getCurrentWeather(latitude, longitude)

    suspend fun fetchCurrentWeatherByQuery(text: String) = apiService.getCurrentWeatherByQuery(text)
}