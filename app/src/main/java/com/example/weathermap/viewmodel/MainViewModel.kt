package com.example.weathermap.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import com.example.weathermap.consts.Const
import com.example.weathermap.geo.Geo
import com.example.weathermap.weather.json.model.BaseWeatherModel
import com.example.weathermap.weather.json.send.SendWeather
import com.example.weathermap.repository.Repository
import kotlinx.coroutines.*
import retrofit2.Response
import ru.redcom.lib.integration.api.client.dadata.DaDataClientException
import ru.redcom.lib.integration.api.client.dadata.DaDataClientFactory
import ru.redcom.lib.integration.api.client.dadata.dto.Address
import androidx.lifecycle.*

@SuppressLint("StaticFieldLeak")
class MainViewModel: ViewModel(){

    private val repository: Repository by lazy { Repository() }
    val myResponseCord: MutableLiveData<Address> = MutableLiveData()
    val myResponseWeather: MutableLiveData<Response<BaseWeatherModel>> = MutableLiveData()

    // Получить "координаты" устройства
    fun getUserCoords(context: Activity) {
        Geo(context).getLastKnownLocation()
    }

    // Получить "координаты" по названию города
    fun postCoord(city: String){
        viewModelScope.launch {
            try {
                val dadata = DaDataClientFactory.getInstance(Const.API_KEY_COORD, Const.SECRET_KEY)
                withContext(Dispatchers.IO){
                    val response: Address = dadata.cleanAddress(city)
                    withContext(Dispatchers.Main){
                        myResponseCord.value = response
                    }
                }
            } catch (e: DaDataClientException){
                Log.d(Const.TAG_FOR_TESTING, e.stackTraceToString())
            }
        }
    }

    // Получить "погоду" города по координатам
    fun postWeather(lat: String, lon: String, appid: String) {
        viewModelScope.launch {
            val sendWeather = SendWeather(lat, lon, appid)
            val response: Response<BaseWeatherModel> = repository.postWeather(sendWeather)
            myResponseWeather.value = response
            Log.d(Const.TAG_FOR_TESTING, "lat: ${myResponseWeather.value?.body()?.lat}")
            Log.d(Const.TAG_FOR_TESTING, "lon: ${myResponseWeather.value?.body()?.lon}")
        }
    }
}