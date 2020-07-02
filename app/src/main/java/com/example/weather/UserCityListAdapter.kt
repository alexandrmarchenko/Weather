package com.example.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_city_list_item.view.*

class UserCityListAdapter(private val listener: (Int) -> Unit) :
    RecyclerView.Adapter<UserCityListAdapter.UserCityListViewHolder>() {

    var dataList: ArrayList<WeatherData> = ArrayList()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCityListViewHolder {
        return UserCityListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_city_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: UserCityListViewHolder, position: Int) {
        holder.bind(dataList[position], this.listener)
    }

    class UserCityListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: WeatherData, listener: (Int) -> Unit) {
            itemView.city_name.text = item.city
            itemView.temperature.text = item.curTemp.toString()
            itemView.humidity_val.text = item.humidity.toString()
            itemView.wind_direction.text = item.windDir
            itemView.wind_speed.text = item.windSpeed.toString()
            itemView.day_night_temperature.text = "${item.dayTemp}/${item.nightTemp}"

            itemView.setOnClickListener{ listener(adapterPosition) }
        }
    }


}