package dev.arj.cuacanusantara.data.weather.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val id: Int,
    val weather: List<Weather>,
    @SerializedName("name")
    val locationName: String,
    val wind: Wind,
    val visibility: Int,
    val clouds: Clouds,
    @SerializedName("main")
    val temperature: Temperature,
    @SerializedName("dt")
    val currentDateTime: Int
)