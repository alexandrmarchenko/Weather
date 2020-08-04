package com.example.weather

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.cityWeatherForecast.cityData.CityData
import kotlinx.android.synthetic.main.fragment_add_city.*

/**
 * A simple [Fragment] subclass.
 */
class AddCityFragment : Fragment() {

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

        setBackBtnListener()

        setEnterCityTextEditListener()

        fillCityList()
    }

    private fun fillCityList() {


        val listener = { city: CityData -> addCity(city) }

        searchCityListAdapter = SearchCityListAdapter(listener)

        cityList.layoutManager = LinearLayoutManager(context)
        cityList.adapter = searchCityListAdapter

        val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.separator));
        cityList.addItemDecoration(itemDecoration);


        //  searchCityListAdapter.dataList = getCityList(cities)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as MainActivity).initService()
    }

    private fun addCity(city: CityData) {

        //val locale = resources.configuration.locale.toLanguageTag()
        val locale = SettingsPresenter.instance.locale

//        val data = DataLoader.load(city, locale, SettingsPresenter.instance.temperature_metric)
        val data = (activity as MainActivity).boundService?.loadWeatherData(
            city,
            locale,
            SettingsPresenter.instance.temperature_metric
        )
        if (data != null)
            WeatherData.instance.add(data)

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

    override fun onStop() {
        (activity as MainActivity).unbindService()
        super.onStop()
    }

    private fun setEnterCityTextEditListener() {

        enter_city.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                    val text = enter_city.text.toString()
                    if (!text.isNullOrBlank()) {
                        fillCityList(text)
                    }
                    return true;
                }
                return false;
            }
        })

    }

    private fun fillCityList(text: String) {

        //    val locale = resources.configuration.locale.toLanguageTag()
//        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//
//        val ims = imm.currentInputMethodSubtype
//
//        val locale = ims.locale

        //Тут надо получать язык клавиатуры
        val locale = SettingsPresenter.instance.locale


        //val data = DataLoader.loadCityData(text, locale)
        val data = (activity as MainActivity).boundService?.loadCityData(text, locale)
        searchCityListAdapter.dataList = data ?: ArrayList()
    }
}
