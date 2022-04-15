package dev.arj.cuacanusantara.data.weather.model

import com.google.gson.annotations.SerializedName

data class Temperature(
    @SerializedName("feels_like")
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("temp_min")
    val tempMin: Double
) {
    fun getTemperatureInCelsius(): String {
        val degree = String.format("%.1f", temp - 273.15)
        return "${degree}°C"
    }
}