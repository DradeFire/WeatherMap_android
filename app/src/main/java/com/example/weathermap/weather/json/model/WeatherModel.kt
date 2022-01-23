package com.example.weathermap.weather.json.model

data class WeatherModel(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)