package dev.arj.cuacanusantara.data.weather.repo

import dev.arj.cuacanusantara.data.weather.mapper.weather.WeatherUiModel
import kotlinx.coroutines.flow.Flow

interface WeatherRepositoryInterface {
    suspend fun fetchCurrentWeather(latitude: String, longitude: String): Flow<WeatherUiModel>
}