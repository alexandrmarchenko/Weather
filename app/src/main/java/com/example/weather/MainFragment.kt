package com.example.weather

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.weather.cityWeatherForecast.CityWeatherForecastData
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlin.math.roundToInt

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    companion object {
        const val CITY_KEY = "CITY"

        fun create(data: CityWeatherForecastData): MainFragment {
            var mainFragment = MainFragment()

            var args = Bundle()
            args.putSerializable("weatherData", data)
            mainFragment.arguments = args

            return mainFragment
        }
    }

    private var link: String = "http://m.accuweather.com/"

    private fun getDataWeather(): CityWeatherForecastData {
        return arguments?.getSerializable("weatherData") as CityWeatherForecastData
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        setCityManagementBtnListener()

        setSettingsPopupBtnListener()

        setDetailBtnListener()

    }

    private fun init() {
        var data = getDataWeather()

        val settings = SettingsPresenter.instance

        city.text = data.city?.localizedName
        if (settings.temperature_metric) {
            currTemp.text =
                data.currentConditions?.temperature?.metric?.value?.roundToInt().toString()
        } else {
            currTemp.text =
                data.currentConditions?.temperature?.imperial?.value?.roundToInt().toString()
        }
        currTempMeasure.text = settings.temperatureMeasureValue

        val today = data.weatherForecast?.dailyForecasts?.get(0)

        todayWeatherTxt.text = today?.day?.shortPhrase
        todayTemp.text =
            "${today?.temperature?.maximum?.value?.roundToInt()} / ${today?.temperature?.minimum?.value?.roundToInt()}"
        todayTempMeasure.text = settings.temperatureMeasureValue

        val tomorrow = data.weatherForecast?.dailyForecasts?.get(1)
        tomorrowWeatherTxt.text = tomorrow?.day?.shortPhrase
        tomorrowTemp.text =
            "${tomorrow?.temperature?.maximum?.value?.roundToInt()} / ${tomorrow?.temperature?.minimum?.value?.roundToInt()}"
        tomorrowTempMeasure.text = settings.temperatureMeasureValue

        val dayAfterTom = data.weatherForecast?.dailyForecasts?.get(2)
        dayAfterTomWeatherTxt.text = dayAfterTom?.day?.shortPhrase
        dayAfterTomTemp.text =
            "${dayAfterTom?.temperature?.maximum?.value?.roundToInt()} / ${dayAfterTom?.temperature?.minimum?.value?.roundToInt()}"
        dayAfterTomTempMeasure.text = settings.temperatureMeasureValue

        link = today?.mobileLink ?: link
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    private fun setDetailBtnListener() {
        details.setOnClickListener {
            val browser = Uri.parse("https://www.accuweather.com")
            val intent = Intent(Intent.ACTION_VIEW, browser)
            //val components = intent.resolveActivity(package)
            //if (components != null) {
            startActivity(intent)
            //}
        }
    }

    private fun setSettingsPopupBtnListener() {

        topAppBar.setOnMenuItemClickListener { item: MenuItem? ->
            when (item?.itemId) {
                R.id.settings -> {
                    callSettingsActivity()
                    true
                }
                R.id.about -> {
                    Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    false
                }
            }
        }

        temperatureTable.todayTemp
    }

    private fun setCityManagementBtnListener() {
        topAppBar?.setNavigationOnClickListener { callCityManagement() }
    }

    private fun callSettingsActivity() {
        val intent = Intent(context, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun callCityManagement() {

        var userCityListFragment = UserCityListFragment()

        var ft = fragmentManager?.beginTransaction()
        ft!!.replace(R.id.fragment_container, userCityListFragment)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.addToBackStack("");
        ft.commit()

    }

}
