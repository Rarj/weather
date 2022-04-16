package dev.arj.cuacanusantara.data.weather.mapper.weather

import androidx.annotation.RawRes
import dev.arj.cuacanusantara.R

enum class StatusWeatherEnum {
    THUNDERSTORM { // 2xx
        override fun getAnimationName(): Int {
            return R.raw.thunderstorm
        }
    },
    DRIZZLE { // 3xx
        override fun getAnimationName(): Int {
            return R.raw.light_rain
        }
    },
    RAIN { // 5xx
        override fun getAnimationName(): Int {
            return R.raw.heavy_rain
        }
    },
    SNOW { // 6xx
        override fun getAnimationName(): Int {
            return R.raw.snow
        }
    },
    ATMOSPHERE { // 7xx
        override fun getAnimationName(): Int {
            return R.raw.sand_dust
        }
    },
    CLEAR { // 800
        override fun getAnimationName(): Int {
            return R.raw.sunny
        }
    },
    CLOUD { // 80x
        override fun getAnimationName(): Int {
            return R.raw.cloudy
        }
    };

    abstract fun getAnimationName(): Int
}

abstract class StatusWeather(private val id: Int) {

    private fun parseWeatherToJsonFileName(): Int {
        return if (id.toString().startsWith("2")) {
            StatusWeatherEnum.THUNDERSTORM.getAnimationName()
        } else if (id.toString().startsWith("3")) {
            StatusWeatherEnum.DRIZZLE.getAnimationName()
        } else if (id.toString().startsWith("5")) {
            StatusWeatherEnum.RAIN.getAnimationName()
        } else if (id.toString().startsWith("6")) {
            StatusWeatherEnum.SNOW.getAnimationName()
        } else if (id.toString().startsWith("7")) {
            StatusWeatherEnum.ATMOSPHERE.getAnimationName()
        } else if (id.toString().startsWith("8")) {
            if (id.toString() == "800") {
                StatusWeatherEnum.CLEAR.getAnimationName()
            } else {
                StatusWeatherEnum.CLOUD.getAnimationName()
            }
        } else {
            StatusWeatherEnum.CLEAR.getAnimationName() // default value
        }
    }

    @RawRes
    fun getStatusWeatherAnimation(): Int {
        return parseWeatherToJsonFileName()
    }
}

data class WeatherUiModel(
    val weatherId: Int,
    val iconUrl: String,
    val locationName: String,
    val fullDescription: String,
    val temperature: String,
    val windSpeed: String,
    val visibility: String,
    val humidity: String
) : StatusWeather(weatherId)