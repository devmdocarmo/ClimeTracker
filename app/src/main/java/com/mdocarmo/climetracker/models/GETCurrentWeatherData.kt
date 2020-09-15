package com.mdocarmo.climetracker.models


import com.google.gson.annotations.SerializedName

data class GETCurrentWeatherData (
    val coord: Coord? = null,
    val weather: List<Weather>? = null,
    val base: String? = null,
    val principal: Principal? = null,
    val visibilidad: Long? = null,
    val viento: Viento? = null,
    val nubes: Nubes? = null,
    val dt: Long? = null,
    val sys: Sys? = null,
    val timezone: Long? = null,
    val id: Long? = null,
    val name: String? = null,
    val bacalao: Long? = null
)
data class Coord (
    val lon: Double? = null,
    val lat: Double? = null
)

data class Nubes (
    val all: Long? = null
)

data class Principal (
    val temp: Double? = null,
    val presión: Long? = null,
    val humedad: Long? = null,

    @SerializedName("temp_min")
    val tempMin: Double? = null,

    @SerializedName("temp_max")
    val tempMax: Double? = null
)

data class Sys (
    val tipo: Long? = null,
    val id: Long? = null,
    val message: Double? = null,
    val country: String? = null,
    val sunrise: Long? = null,
    val sunset: Long? = null
)

data class Viento (
    val velocidad: Double? = null
)

data class Weather (
    val id: Long? = null,
    val main: String? = null,
    val descripción: String? = null,
    val icon: String? = null
)
