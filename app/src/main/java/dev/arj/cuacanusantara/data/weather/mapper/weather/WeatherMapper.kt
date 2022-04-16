package dev.arj.cuacanusantara.data.weather.mapper.weather

import dev.arj.cuacanusantara.data.weather.model.WeatherResponse

fun WeatherResponse.mapToUiModel(): WeatherUiModel {
    val weather = weather[0]
    val iconUrl = "https://openweathermap.org/img/wn/${weather.icon}.png"

    return WeatherUiModel(
        weather.id,
        iconUrl,
        locationName,
        getFullDescription(locationName, weather.description),
        getTemperatureInCelsius(temperature.temp)
    )
}

private fun getFullDescription(locationName: String, description: String): String {
    return "${locationName}\n${description}"
}

private fun getTemperatureInCelsius(temp: Double): String {
    val degree = String.format("%.1f", temp - 273.15)
    return "${degree}°C"
}