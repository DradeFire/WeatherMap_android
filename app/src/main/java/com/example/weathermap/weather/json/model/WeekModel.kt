package com.example.weathermap.weather.json.model

data class WeekModel(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val feels_like: FeelsLikeModel,
    val humidity: Int,
    val moon_phase: Double,
    val moonrise: Int,
    val moonset: Int,
    val pop: Double,
    val pressure: Int,
    val rain: Double,
    val sunrise: Int,
    val sunset: Int,
    val temp: TempModel,
    val uvi: Double,
    val weather: List<WeatherModel>,
    val wind_deg: Int,
    val wind_speed: Double
)