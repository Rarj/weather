package dev.arj.cuacanusantara.domain.weather.usecase

import dev.arj.cuacanusantara.data.weather.mapper.weather.WeatherUiModel
import dev.arj.cuacanusantara.data.weather.repo.WeatherRepository
import dev.arj.cuacanusantara.data.weather.repo.WeatherRepositoryInterface
import kotlinx.coroutines.flow.Flow

class WeatherUseCase(
    private val weatherRepository: WeatherRepository
) : WeatherRepositoryInterface {

    override suspend fun fetchCurrentWeather(
        latitude: String,
        longitude: String
    ): Flow<WeatherUiModel> {
        return weatherRepository.fetchCurrentWeather(latitude, longitude)
    }

    override suspend fun fetchCurrentWeatherByQuery(text: String): Flow<WeatherUiModel> {
        return weatherRepository.fetchCurrentWeatherByQuery(text)
    }
}