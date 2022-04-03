package dev.arj.cuacanusantara.network

import dev.arj.cuacanusantara.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Long,
        @Query("long") longitude: Long,
        @Query("appid") api_key: String = BuildConfig.API_KEY
    )

}