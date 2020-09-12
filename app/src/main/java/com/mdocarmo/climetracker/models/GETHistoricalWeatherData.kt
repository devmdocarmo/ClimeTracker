package com.mdocarmo.climetracker.models

data class GETHistoricalWeatherData (
    val current: Current? = null,
    val hourly: List<Hourly>? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    val timezone: String? = null,
    val timezoneOffset: Long? = null
)

data class Current (
    val clouds: Long? = null,
    val dewPoint: Double? = null,
    val dt: Long? = null,
    val feelsLike: Double? = null,
    val humidity: Long? = null,
    val pressure: Long? = null,
    val sunrise: Long? = null,
    val sunset: Long? = null,
    val temp: Double? = null,
    val uvi: Double? = null,
    val weather: List<WeatherHistorical>? = null,
    val windDeg: Long? = null,
    val windGust: Double? = null,
    val windSpeed: Double? = null
)

data class WeatherHistorical (
    val the0: The0? = null
)

data class The0 (
    val description: String? = null,
    val icon: String? = null,
    val id: Long? = null,
    val main: String? = null
)

data class Hourly (
    val the0: Current? = null
)
