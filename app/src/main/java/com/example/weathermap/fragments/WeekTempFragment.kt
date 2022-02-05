package com.example.weathermap.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathermap.MainActivity
import com.example.weathermap.R
import com.example.weathermap.consts.Const
import com.example.weathermap.databinding.FragmentWeekTempBinding
import com.example.weathermap.fragments.adapters.AdapterWeek
import com.example.weathermap.geo.data.models.GeoCoordinates
import com.example.weathermap.viewmodel.MainViewModel
import com.example.weathermap.weatherdata.models.DayTempModel
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*
import kotlin.math.roundToInt

@DelicateCoroutinesApi
class WeekTempFragment : Fragment() {

    private lateinit var binding: FragmentWeekTempBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        (requireActivity() as MainActivity).title = this.getText(R.string.pop_on_week)
        binding = FragmentWeekTempBinding.inflate(inflater, container, false)

        startBind()

        return binding.root
    }

    private fun startBind() {
        bindButtonsListener()
        startUserTemp()
        bindObservers()
    }

    private fun bindObservers(){
        viewModel.myResponseCord.observeForever{ responseCoord ->
            val coordClass = GeoCoordinates(
                responseCoord.geoLat.toString(),
                responseCoord.geoLon.toString()
            )

            setWeatherByCoordinates(coordClass)
        }
        viewModel.myResponseWeather.observeForever{ response ->
            if(response.isSuccessful) {

                Log.d(Const.TAG_FOR_TESTING,"After lat: " + viewModel.myResponseWeather.value?.body()?.lat /*response.body()?.lat.toString()*/)
                Log.d(Const.TAG_FOR_TESTING,"After lon: " + viewModel.myResponseWeather.value?.body()?.lon/*response.body()?.lon.toString()*/)

                // Задавание rcView погоды по дню
                val arrDayTempModel = ArrayList<DayTempModel>()
                val date = Calendar.getInstance()

                val daily = response.body()?.daily!!
                for (index in 0..6){
                    val day: String = if(index == 0)
                        "Today"
                    else
                        Const.DayOfWeek[date.get(Calendar.DAY_OF_WEEK) - 1]

                    arrDayTempModel.add(DayTempModel(
                        day,
                        daily[index].temp.day.toString() + "°C",
                        daily[index].wind_speed.toString() + " м/с",
                        (daily[0].pop * 100.0).roundToInt().toString() + "%",
                        daily[index].humidity.toString() + "%",
                        daily[index].temp.morn.toString() + "°C",
                        daily[index].temp.eve.toString() + "°C",
                        daily[index].temp.night.toString() + "°C"
                    ))
                    date.add(Calendar.HOUR, 24)
                }

                // Настройка адаптера
                val adapter = AdapterWeek(requireContext())
                binding.rcViewWeek.adapter = adapter
                binding.rcViewWeek.layoutManager = LinearLayoutManager(requireContext())
                adapter.setWeekList(arrDayTempModel)

                setNameSity(binding.inputCity.text.toString())

                Log.d(Const.TAG_FOR_TESTING, getString(R.string.all_work_fine))
            } else {
                Log.d(Const.TAG_FOR_TESTING, "Weather problem: ${response.errorBody()}: ${response.message()} : ${response.code()}")
            }
        }
    }

    private fun bindButtonsListener() = with(binding) {

        btChangeOnDay.setOnClickListener {
            findNavController().navigate(R.id.action_weekTempFragment_to_todayTempFragment)
        }

        btFindCity.setOnClickListener {
            val nameSity = inputCity.text.toString()
            if(nameSity.isNotEmpty())
                setWeatherByCityName(nameSity)
            else
                startUserTemp()
        }
    }

    private fun setWeatherByCityName(nameCity: String){
        viewModel.postCoord(nameCity)
        binding.textCityWeek.text = nameCity
    }

    private fun setWeatherByCoordinates(coordinates: GeoCoordinates){
        viewModel.postWeather(coordinates.lat, coordinates.lon, Const.API_KEY_WEATHER)
    }

    private fun startUserTemp() {
        viewModel.getUserCoords(requireActivity())
        LocationServices.getFusedLocationProviderClient(requireContext()).lastLocation.addOnSuccessListener { location ->
            if(location == null){
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(requireActivity().parent, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
                }
                return@addOnSuccessListener
            }

            val coordinates = GeoCoordinates(location.latitude.toString().substring(0, 5),
                location.longitude.toString().substring(0, 5))

            setWeatherByCoordinates(coordinates)
        }
    }

    private fun setNameSity(city: String) = with(binding){
        if(city.isEmpty())
            textCityWeek.text = getString(R.string.this_city)
        else
            textCityWeek.text = city
    }

}