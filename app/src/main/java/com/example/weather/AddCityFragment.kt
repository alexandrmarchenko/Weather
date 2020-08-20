package com.example.weather

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.bd.App
import com.example.weather.bd.dao.WeatherDao
import com.example.weather.bd.model.CitySearchHist
import com.example.weather.cityWeatherForecast.cityData.CityData
import com.example.weather.geo.Geo
import com.example.weather.geo.GeoModel
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_add_city.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class AddCityFragment : Fragment(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null
    private var mapsSupported = true
    private val geoModel: GeoModel = GeoModel()

    private lateinit var searchCityListAdapter: SearchCityListAdapter

    private var autoComplete: List<String> = ArrayList()

    private var weatherDb: WeatherDao? = App.getInstance()?.getWeatherDao()

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

        initAutoComplete()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {
            MapsInitializer.initialize(activity)
        } catch (ex: GooglePlayServicesNotAvailableException) {
            mapsSupported = false;
        }

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
        initializeMap();

        initAddressObserver()

    }

    private fun initAddressObserver() {
        geoModel.getAdressData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                enterCity.setText(it?.locality)
                enterCity.onEditorAction(EditorInfo.IME_ACTION_SEARCH)
            }
        })
    }

    private fun initializeMap() {
        if (googleMap == null && mapsSupported) {
            mapView.getMapAsync(this)
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        googleMap = p0

        initMapListeners()

        val position = Geo.getInstance(requireContext()).currentPosition
        if (position != null) {
            googleMap?.addMarker(MarkerOptions().position(position).title("Current position"))
            googleMap?.moveCamera(CameraUpdateFactory.newLatLng(position))
        }
        mapView.onResume()
    }

    private fun initMapListeners() {
        googleMap?.setOnMapLongClickListener(OnMapLongClickListener { latLng ->
            geoModel.getAdress(requireContext(), latLng)
        })
        googleMap?.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
            override fun onMarkerClick(p0: Marker?): Boolean {
                p0?.position?.let { position ->
                    geoModel.getAdress(requireContext(), position)
                    return true
                }
                return false
            }
        })
    }

    private fun initAutoComplete() {
        autoComplete = weatherDb?.selectCityOrderDesc() ?: ArrayList()

        val adapter = ArrayAdapter<String>(
            requireContext(), android.R.layout.simple_dropdown_item_1line, autoComplete
        )
        enterCity.setAdapter(adapter)
        enterCity.threshold = 2
    }

    private fun addCitySearchHist(text: String) {
        val calender = Calendar.getInstance()
        val hist = CitySearchHist(text, calender.time)
        weatherDb?.insertCitySearch(hist)
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

    private fun addCity(city: CityData) {

        //val locale = resources.configuration.locale.toLanguageTag()
        val locale = SettingsPresenter.instance.locale

        val data = DataLoader.load(city, locale, SettingsPresenter.instance.temperature_metric)
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

    private fun setEnterCityTextEditListener() {

        enterCity.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                    val text = enterCity.text.toString()
                    if (!text.isNullOrBlank()) {
                        fillCityList(text)
                        addCitySearchHist(text)
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


        val data = DataLoader.loadCityData(text, locale)
        searchCityListAdapter.dataList = data
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onDestroy()
    }
}
