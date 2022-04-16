package dev.arj.cuacanusantara.data.weather.mapper.weather

import dev.arj.cuacanusantara.data.weather.model.WeatherResponse

fun WeatherResponse.mapToUiModel(): WeatherUiModel {
    val weather = weather[0]
    val iconUrl = "https://openweathermap.org/img/wn/${weather.icon}.png"

    return WeatherUiModel(
        weatherId = weather.id,
        iconUrl = iconUrl,
        locationName = locationName,
        fullDescription = getFullDescription(locationName, weather.description),
        temperature = getTemperatureInCelsius(temperature.temp),
        windSpeed = "${wind.speed} KM/h",
        visibility = "${visibility / 1000} KM",
        humidity = "${temperature.humidity}%",
    )
}

private fun getFullDescription(locationName: String, description: String): String {
    return "${locationName}\n${description}"
}

private fun getTemperatureInCelsius(temp: Double): String {
    val degree = String.format("%.1f", temp - 273.15)
    return "${degree}°C"
}