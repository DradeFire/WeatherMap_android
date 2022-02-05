package com.example.weathermap.api

import com.example.weathermap.weatherdata.json.model.BaseWeatherModel
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitApi{

    @POST("data/2.5/onecall")
    suspend fun postWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru"
    ): Response<BaseWeatherModel>
}