package dev.arj.cuacanusantara.data.weather.repo

import dev.arj.cuacanusantara.data.weather.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepositoryInterface {
    suspend fun fetchCurrentWeather(latitude: String, longitude: String): Flow<WeatherResponse>
}