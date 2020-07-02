package com.example.weather

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.add_city_list_item.view.*
import kotlinx.android.synthetic.main.fragment_add_city.*

/**
 * A simple [Fragment] subclass.
 */
class AddCityFragment : Fragment() {

    private lateinit var cities: Array<String>

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

        this.fillCityList(cities);

        setBackBtnListener()

        setEnterCityTextEditListener()
    }

    private fun setBackBtnListener() {
        back.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun setEnterCityTextEditListener() {
        enter_city.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val cList = cities.filter { city ->
                    city.toUpperCase().contains(s.toString().toUpperCase())
                }
                fillCityList(cList.toTypedArray());
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun fillCityList(cities: Array<String>) {


        cityList.removeAllViews()
        for (city in cities) {
            val cityTextView =
                LayoutInflater.from(context).inflate(R.layout.add_city_list_item, null)

            cityTextView.setOnClickListener {
                MainActivity.addCityData(city)

                var userCityListFragment = UserCityListFragment()

                var ft = fragmentManager?.beginTransaction()
                ft?.replace(R.id.fragment_container, userCityListFragment)
                ft?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

                ft?.commit()
            }

            cityTextView.city.text = city
            cityList.addView(cityTextView)
        }
    }
}
