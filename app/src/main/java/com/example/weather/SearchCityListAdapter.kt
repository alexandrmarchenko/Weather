package com.example.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.cityWeatherForecast.cityData.CityData
import kotlinx.android.synthetic.main.search_city_list_item.view.*
import kotlinx.android.synthetic.main.user_city_list_item.view.city_name

class SearchCityListAdapter(private val listener: (CityData) -> Unit) :
    RecyclerView.Adapter<SearchCityListAdapter.SearchCityListViewHolder>() {

    var dataList: ArrayList<CityData> = ArrayList()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCityListViewHolder {
        return SearchCityListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.search_city_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: SearchCityListViewHolder, position: Int) {
        holder.bind(dataList[position], this.listener)
    }

    class SearchCityListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: CityData, listener: (CityData) -> Unit) {
            itemView.city_name.text = item.localizedName
            itemView.country.text =
                "${item.administrativeArea.localizedName}, ${item.country.localizedName}"

            itemView.setOnClickListener { listener(item) }
        }
    }
}