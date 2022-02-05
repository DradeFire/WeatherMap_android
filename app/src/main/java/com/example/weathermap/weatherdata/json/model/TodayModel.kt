package com.example.weathermap.weatherdata.json.model

data class TodayModel(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    val weather: List<WeatherModel>,
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double
)