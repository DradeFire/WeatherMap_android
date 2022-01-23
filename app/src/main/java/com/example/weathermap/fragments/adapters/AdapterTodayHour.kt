package com.example.weathermap.fragments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermap.databinding.ItemDayTempBinding
import com.example.weathermap.weather.models.HourTempModel

class AdapterTodayHour : RecyclerView.Adapter<AdapterTodayHour.ItemViewHolder>() {

    private var taskList = ArrayList<HourTempModel>()

    class ItemViewHolder(private val binding: ItemDayTempBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(hourTempModel: HourTempModel) = with(binding){
            textItemHourTemp.text = hourTempModel.temp
            textItemHourTime.text = hourTempModel.hour
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemDayTempBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = taskList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDayList(hourTempModel: ArrayList<HourTempModel>){
        taskList = hourTempModel
        notifyDataSetChanged()
    }

}