package dev.arj.cuacanusantara.data.weather.mapper.weather

enum class StatusWeatherEnum {
    THUNDERSTORM { // 2xx
        override fun getAnimationName(): String {
            return "thunderstorm.json"
        }
    },
    DRIZZLE { // 3xx
        override fun getAnimationName(): String {
            return "light_rain.json"
        }
    },
    RAIN { // 5xx
        override fun getAnimationName(): String {
            return "heavy_rain.json"
        }
    },
    SNOW { // 6xx
        override fun getAnimationName(): String {
            return "snow.json"
        }
    },
    ATMOSPHERE { // 7xx
        override fun getAnimationName(): String {
            return "sand_dust.json"
        }
    },
    CLEAR { // 800
        override fun getAnimationName(): String {
            return "sunny.json"
        }
    },
    CLOUD { // 80x
        override fun getAnimationName(): String {
            return "cloudy.json"
        }
    };

    abstract fun getAnimationName(): String
}

abstract class StatusWeather(private val id: Int) {

    private fun parseWeatherToJsonFileName(): String {
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

    fun getStatusWeatherAnimation(): String {
        return parseWeatherToJsonFileName()
    }
}

data class WeatherUiModel(
    val weatherId: Int,
    val iconUrl: String,
    val locationName: String,
    val fullDescription: String,
    val temperature: String,
) : StatusWeather(weatherId)