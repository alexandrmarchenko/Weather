package com.example.weather

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    companion object {
        const val CITY_KEY = "CITY"

        fun create(data: WeatherData): MainFragment {
            var mainFragment = MainFragment()

            var args = Bundle()
            args.putSerializable("weatherData", data)
            mainFragment.arguments = args

            return mainFragment
        }
    }

    fun getDataWeather(): WeatherData {
        return arguments?.getSerializable("weatherData") as WeatherData
    }

    private val REQUEST_GET_DATA_TYPE = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var data = getDataWeather()

        city.text = data.city
        currTemp.text = data.curTemp.toString()


        setCityManagementBtnListener()

        setSettingsPopupBtnListener()

        setDetailBtnListener()

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
        settingsPopup.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                showSettingsPopup(v)
            }
        })

        temperatureTable.todayTemp
    }

    private fun setCityManagementBtnListener() {
        cityManagement?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                callCityManagement()
            }
        })
    }

    fun showSettingsPopup(view: View?) {
        val wrapper = ContextThemeWrapper(context, R.style.PopupMenu)
        val popupMenu = PopupMenu(wrapper, view)
        popupMenu.inflate(R.menu.popup_menu)

        popupMenu.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
            PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.settings -> {
                        callSettingsActivity()
                        return true
                    }
                    R.id.about -> {
                        Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
                        return true
                    }
                    else -> {
                        return true
                    }
                }
            }
        })

        popupMenu.show()
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
