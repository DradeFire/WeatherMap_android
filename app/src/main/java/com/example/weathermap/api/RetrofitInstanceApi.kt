package com.example.weathermap.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstanceApi{

    private val retrofitWeather by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiWeather: RetrofitApi by lazy {
        retrofitWeather.create(RetrofitApi::class.java)
    }

}