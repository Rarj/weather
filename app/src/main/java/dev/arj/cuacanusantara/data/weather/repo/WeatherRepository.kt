package dev.arj.cuacanusantara.data.weather.repo

import dev.arj.cuacanusantara.data.weather.model.WeatherResponse
import dev.arj.cuacanusantara.data.weather.source.WeatherRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class WeatherRepository(
    private val remoteDataSource: WeatherRemoteDataSource
) : WeatherRepositoryInterface {

    override suspend fun fetchCurrentWeather(
        latitude: String,
        longitude: String
    ): Flow<WeatherResponse> {
        return flow {
            val response = remoteDataSource.fetchCurrentWeather(latitude, longitude)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }
}