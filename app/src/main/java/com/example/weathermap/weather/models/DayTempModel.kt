package com.example.weathermap.weather.models

data class DayTempModel(
    val dayOfWeek: String = "",
    val temp_day: String = "",
    val windSpeed: String = "",
    val precipitation: String = "",
    val humidity: String = "",
    val temp_morn: String = "",
    val temp_eve: String = "",
    val temp_night: String = ""
)
