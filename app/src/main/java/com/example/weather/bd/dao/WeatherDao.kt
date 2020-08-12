package com.example.weather.bd.dao

import androidx.room.*
import com.example.weather.bd.model.CitySearchHist

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCitySearch(city: CitySearchHist)

    @Update
    fun updateCitySearch(city: CitySearchHist)

    @Delete
    fun deleteCitySearch(city: CitySearchHist)


    @Query("SELECT * FROM CitySearchHist")
    fun selectAllHist(): List<CitySearchHist>

    @Query("SELECT * FROM CitySearchHist where id=:id")
    fun selectCHistById(id: Long): CitySearchHist

    @Query("SELECT search_text FROM CitySearchHist order by date desc")
    fun selectCityOrderDesc(): List<String>
}