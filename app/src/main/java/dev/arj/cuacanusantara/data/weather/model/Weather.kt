package dev.arj.cuacanusantara.data.weather.model

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)