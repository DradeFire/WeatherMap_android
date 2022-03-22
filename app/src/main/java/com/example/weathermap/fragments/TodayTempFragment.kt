package com.example.weathermap.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathermap.R
import com.example.weathermap.consts.Const
import com.example.weathermap.databinding.FragmentTodayTempBinding
import com.example.weathermap.fragments.adapters.AdapterTodayHour
import com.example.weathermap.geo.data.models.GeoCoordinates
import com.example.weathermap.viewmodel.MainViewModel
import com.example.weathermap.weatherdata.models.HourTempModel
import com.google.android.gms.location.LocationServices
import java.util.*
import kotlin.math.roundToInt

class TodayTempFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentTodayTempBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        (requireActivity() as MainActivity).title = this.getText(R.string.pop_on_day)
        binding = FragmentTodayTempBinding.inflate(inflater, container, false)

        startBind()

        return binding.root
    }

    private fun startBind() {
        bindButtonsListener()
        if(viewModel.cityToSearch == null)
            startUserTemp()
        else
            binding.inputCity.setText(viewModel.cityToSearch)
        bindObservers()
    }

    private fun bindObservers(){
        viewModel.myResponseCord.observe(viewLifecycleOwner) { responseCoord ->
            val coordClass = GeoCoordinates(
                responseCoord.geoLat.toString(),
                responseCoord.geoLon.toString()
            )
            Log.d(Const.TAG_FOR_TESTING, responseCoord.geoLat.toString())
            Log.d(Const.TAG_FOR_TESTING, responseCoord.geoLon.toString())

            setWeatherByCoordinates(coordClass)
        }
        viewModel.myResponseWeather.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                Log.d(Const.TAG_FOR_TESTING, "After lat: " + response.body()?.lat.toString())
                Log.d(Const.TAG_FOR_TESTING, "After lon: " + response.body()?.lon.toString())
                Log.d(
                    Const.TAG_FOR_TESTING,
                    "After lat: " + viewModel.myResponseWeather.value?.body()?.lat.toString()
                )
                Log.d(
                    Const.TAG_FOR_TESTING,
                    "After lon: " + viewModel.myResponseWeather.value?.body()?.lon.toString()
                )

                // Задавание rcView почасовой температуры сегодня
                val arrHourTempModel = ArrayList<HourTempModel>()
                val date = Calendar.getInstance()

                for (hour in date.get(Calendar.HOUR_OF_DAY)..23) {
                    val tempOnHour = response.body()?.hourly?.get(hour)?.temp.toString()
                        .substring(0, response.body()?.hourly?.get(hour)?.temp.toString().length - 1) + "°C"
                    arrHourTempModel
                        .add(HourTempModel(tempOnHour, "$hour:00"))
                }

                // Создание rcView и адаптера
                val adapter = AdapterTodayHour()
                binding.rcViewToday.adapter = adapter
                binding.rcViewToday.layoutManager = LinearLayoutManager(
                    requireActivity(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapter.setDayList(arrHourTempModel)

                // Задавание сегодняшней температуры
                setTempToday(
                    response.body()?.current?.temp.toString()
                        .substring(0, response.body()?.current?.temp.toString().length - 1)
                ) // Температура сейчас
                setOptionalChar(
                    response.body()?.current?.wind_speed?.toString() + " м/с",
                    (response.body()?.daily?.get(0)?.pop!! * 100.0).roundToInt().toString() + "%",
                    response.body()?.current?.humidity.toString() + "%"
                )
                setNameSity(binding.inputCity.text.toString()) // Название города

                Log.d(Const.TAG_FOR_TESTING, getString(R.string.all_work_fine))
            } else {
                Log.d(
                    Const.TAG_FOR_TESTING,
                    "Weather problem: ${response.errorBody()}: ${response.message()} : ${response.code()}"
                )
            }
        }
    }

    private fun bindButtonsListener() = with(binding){
        btChangeOnWeek.setOnClickListener {
            findNavController().navigate(R.id.action_todayTempFragment_to_weekTempFragment)
        }
        btFindCity.setOnClickListener {
            val nameSity = inputCity.text.toString()
            if(nameSity.isNotEmpty()){
                viewModel.cityToSearch = nameSity
                setWeatherByCityName(nameSity)
            }
            else {
                viewModel.cityToSearch = null
                startUserTemp()
            }

        }
    }

    private fun setWeatherByCityName(nameCity: String){
        viewModel.postCoord(nameCity)
    }

    private fun startUserTemp(){
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

    private fun setWeatherByCoordinates(coordinates: GeoCoordinates){
        viewModel.postWeather(coordinates.lat, coordinates.lon, Const.API_KEY_WEATHER)
    }

//    private fun <T> MutableLiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
//        observe(lifecycleOwner, object : Observer<T> {
//            override fun onChanged(t: T?) {
//                observer.onChanged(t)
//                removeObserver(this)
//            }
//        })
//    }

    private fun setNameSity(city: String) = with(binding){
        if(viewModel.cityToSearch == null)
            textCity.text = "Текущий город"
        else
            textCity.text = viewModel.cityToSearch
    }

    @SuppressLint("SetTextI18n")
    private fun setTempToday(temp: String) = with(binding){
        baseDayContainDay.textTemp.text = "%.1f".format(temp.toDouble()) + "°C"
    }

    @SuppressLint("SetTextI18n")
    private fun setOptionalChar(windSpeed: String, precipitation: String, humidity: String) = with(binding){
        baseDayContainDay.textWindSpeed.text = getString(R.string.windSpeed) + ": $windSpeed"
        baseDayContainDay.textPrecipitation.text = getString(R.string.precipitation) + ": $precipitation"
        baseDayContainDay.textHumidity.text = getString(R.string.humidity) + ": $humidity"
    }

}