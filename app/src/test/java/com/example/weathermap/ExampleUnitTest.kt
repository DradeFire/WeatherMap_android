package com.example.weathermap

import com.example.weathermap.consts.Const
import com.example.weathermap.repository.Repository
import com.example.weathermap.weatherdata.json.model.BaseWeatherModel
import com.example.weathermap.weatherdata.json.send.SendWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.Response
import ru.redcom.lib.integration.api.client.dadata.DaDataClient
import ru.redcom.lib.integration.api.client.dadata.DaDataClientException
import ru.redcom.lib.integration.api.client.dadata.DaDataClientFactory
import ru.redcom.lib.integration.api.client.dadata.dto.Address

class ExampleUnitTest {

    companion object {
        const val CITY = "Moscow"
        const val LAT = "55.75"
        const val LON = "37.61"
    }
    private lateinit var dadata: DaDataClient
    private var repository: Repository = Repository()

    @Nested
    inner class REST_testing{

        @Test
        fun test_rest_weather(){
            runBlocking(Dispatchers.IO) {
                val sendWeather = SendWeather(LAT, LON, Const.API_KEY_WEATHER)
                val response: Response<BaseWeatherModel> = repository.postWeather(sendWeather)
                assertEquals("Europe/Moscow", response.body()?.timezone)
                assertEquals("55", response.body()?.lat.toString().substring(0, 2))
                assertEquals("37", response.body()?.lon.toString().substring(0, 2))
            }
        }

        @Test
        @Disabled
        fun test_rest_geo(){
            dadata = DaDataClientFactory.getInstance(Const.API_KEY_COORD, Const.SECRET_KEY)

            runBlocking(Dispatchers.IO) {
                try {
                    val response: Address = dadata.cleanAddress(CITY)

                    assertEquals("Moscow", response.source)
                    assertEquals("Москва", response.region)
                    assertEquals("г", response.regionType)
                    assertEquals("Россия", response.country)
                } catch (e: DaDataClientException){
                    assert(false)
                }
            }
        }

    }


}