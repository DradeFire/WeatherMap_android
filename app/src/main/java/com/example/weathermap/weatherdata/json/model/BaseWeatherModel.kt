package com.example.weathermap.weatherdata.json.model

data class BaseWeatherModel(
    val alerts: List<Any>,
    val current: CurrentModel,
    val daily: List<WeekModel>,
    val hourly: List<TodayModel>,
    val lat: Double,
    val lon: Double,
    val minutely: List<Any>,
    val timezone: String,
    val timezone_offset: Int
)