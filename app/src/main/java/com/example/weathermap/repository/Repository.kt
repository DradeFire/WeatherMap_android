package com.example.weathermap.repository

import com.example.weathermap.api.RetrofitInstanceApi
import com.example.weathermap.weatherdata.json.model.BaseWeatherModel
import com.example.weathermap.weatherdata.json.send.SendWeather
import retrofit2.Response

class Repository {

    suspend fun postWeather(sendWeather: SendWeather): Response<BaseWeatherModel> {
        return RetrofitInstanceApi.apiWeather.postWeather(sendWeather.lat, sendWeather.lon, sendWeather.appid)
    }

}