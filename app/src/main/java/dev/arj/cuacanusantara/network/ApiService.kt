package dev.arj.cuacanusantara.network

import dev.arj.cuacanusantara.BuildConfig
import dev.arj.cuacanusantara.data.weather.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") api_key: String = BuildConfig.API_KEY
    ): WeatherResponse

}