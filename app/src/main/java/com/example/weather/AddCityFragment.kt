package com.example.weather

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_add_city.*

/**
 * A simple [Fragment] subclass.
 */
class AddCityFragment : Fragment() {

    private lateinit var cities: Array<String>
    private lateinit var searchCityListAdapter: SearchCityListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cities = resources.getStringArray(R.array.cities)

        setBackBtnListener()

        setEnterCityTextEditListener()

        fillCityList()
    }

    private fun fillCityList() {


        val listener = { city: String -> addCity(city) }

        searchCityListAdapter = SearchCityListAdapter(listener)

        cityList.layoutManager = LinearLayoutManager(context)
        cityList.adapter = searchCityListAdapter

        val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.separator));
        cityList.addItemDecoration(itemDecoration);



        searchCityListAdapter.dataList = getCityList(cities)

    }

    private fun addCity(city: String) {

        MainActivity.addCityData(city)

        var userCityListFragment = UserCityListFragment()

        var ft = fragmentManager?.beginTransaction()
        ft?.replace(R.id.fragment_container, userCityListFragment)
        ft?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

        ft?.commit()
    }


    private fun setBackBtnListener() {
        topAppBar.setNavigationOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun setEnterCityTextEditListener() {

        enter_city.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val cList = cities.filter { city ->
                    city.toUpperCase().contains(s.toString().toUpperCase())
                }
                searchCityListAdapter.dataList = getCityList(cList.toTypedArray())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun getCityList(cities: Array<String>): ArrayList<CityData> {

        val cityList = ArrayList<CityData>()
        val country = "Россия"
        val adminArea = "adminArea"
        for (city in cities) {

            cityList.add(CityData(city, country, adminArea))
        }

        return cityList
    }
}
