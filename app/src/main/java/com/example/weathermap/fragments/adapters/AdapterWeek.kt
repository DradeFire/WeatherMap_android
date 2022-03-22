package com.example.weathermap.fragments.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermap.R
import com.example.weathermap.databinding.ItemWeekTempBinding
import com.example.weathermap.weatherdata.models.DayTempModel

class AdapterWeek(private val context: Context)
    : RecyclerView.Adapter<AdapterWeek.ItemViewHolder>() {

    private var arrDayTempModel = ArrayList<DayTempModel>()

    class ItemViewHolder(private val binding: ItemWeekTempBinding): RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(dayTempModel: DayTempModel, context: Context) {
            binding.baseDayContWeek.textTemp.text = "%.1f".format(dayTempModel.temp_day.substring(
                0, dayTempModel.temp_day.length - 2
            ).toDouble()
            ) + dayTempModel.temp_day.substring(
                dayTempModel.temp_day.length - 2
            )
            binding.baseDayContWeek.textHumidity.text = context.getString(R.string.humidity) + ": ${dayTempModel.humidity}"
            binding.baseDayContWeek.textPrecipitation.text = context.getString(R.string.precipitation) + ": ${dayTempModel.precipitation}"
            binding.baseDayContWeek.textWindSpeed.text = context.getString(R.string.windSpeed) + ": ${dayTempModel.windSpeed}"
            binding.baseDayContWeek.textToday.text = dayTempModel.dayOfWeek

            binding.weekDayElMorn.textItemHourTime.text = context.getString(R.string.morning)
            binding.weekDayElMorn.textItemHourTemp.text = "%.1f".format(dayTempModel.temp_morn.substring(
                        0, dayTempModel.temp_morn.length - 2
                    ).toDouble()
                ) + dayTempModel.temp_morn.substring(
                dayTempModel.temp_morn.length - 2
            )


            binding.weekDayElEve.textItemHourTime.text = context.getString(R.string.evening)
            binding.weekDayElEve.textItemHourTemp.text = "%.1f".format(dayTempModel.temp_eve.substring(
                         0, dayTempModel.temp_eve.length - 2
                    ).toDouble()
                ) + dayTempModel.temp_eve.substring(
                dayTempModel.temp_eve.length - 2
            )

            binding.weekDayElNight.textItemHourTime.text = context.getString(R.string.night)
            binding.weekDayElNight.textItemHourTemp.text = "%.1f".format(dayTempModel.temp_night.substring(
                        0, dayTempModel.temp_night.length - 2
                    ).toDouble()
                ) + dayTempModel.temp_night.substring(
                dayTempModel.temp_night.length - 2
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemWeekTempBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = arrDayTempModel[position]
        holder.bind(item, context)
    }

    override fun getItemCount(): Int {
        return arrDayTempModel.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setWeekList(arrDayTempModel: ArrayList<DayTempModel>){
        this.arrDayTempModel = arrDayTempModel
        notifyDataSetChanged()
    }
}