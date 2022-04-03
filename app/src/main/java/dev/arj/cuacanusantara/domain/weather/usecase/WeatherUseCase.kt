package dev.arj.cuacanusantara.domain.weather.usecase

import dev.arj.cuacanusantara.data.weather.model.WeatherResponse
import dev.arj.cuacanusantara.data.weather.repo.WeatherRepository
import dev.arj.cuacanusantara.data.weather.repo.WeatherRepositoryInterface
import kotlinx.coroutines.flow.Flow

class WeatherUseCase(
    private val weatherRepository: WeatherRepository
) : WeatherRepositoryInterface {

    override suspend fun fetchCurrentWeather(
        latitude: String,
        longitude: String
    ): Flow<WeatherResponse> {
        return weatherRepository.fetchCurrentWeather(latitude, longitude)
    }
}