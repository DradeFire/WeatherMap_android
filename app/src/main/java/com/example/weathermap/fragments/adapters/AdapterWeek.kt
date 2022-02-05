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
            binding.baseDayContWeek.textTemp.text = dayTempModel.temp_day
            binding.baseDayContWeek.textHumidity.text = context.getString(R.string.humidity) + ": ${dayTempModel.humidity}"
            binding.baseDayContWeek.textPrecipitation.text = context.getString(R.string.precipitation) + ": ${dayTempModel.precipitation}"
            binding.baseDayContWeek.textWindSpeed.text = context.getString(R.string.windSpeed) + ": ${dayTempModel.windSpeed}"
            binding.baseDayContWeek.textToday.text = dayTempModel.dayOfWeek

            binding.weekDayElMorn.textItemHourTime.text = context.getString(R.string.morning)
            binding.weekDayElMorn.textItemHourTemp.text = dayTempModel.temp_morn

            binding.weekDayElEve.textItemHourTime.text = context.getString(R.string.evening)
            binding.weekDayElEve.textItemHourTemp.text = dayTempModel.temp_eve

            binding.weekDayElNight.textItemHourTime.text = context.getString(R.string.night)
            binding.weekDayElNight.textItemHourTemp.text = dayTempModel.temp_night
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