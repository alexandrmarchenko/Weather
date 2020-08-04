package com.example.weather

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.weather.cityWeatherForecast.CityWeatherForecastData
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*


class MainActivity : AppCompatActivity() {

    private val TAG = "WEATHER"
    private val FILE_NAME = "date.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setThemeDependTime()
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {

            init()


        }
    }

    private fun init() {

        settingsInit()

        cityDataInit()

        callFragment()
    }

    private fun callFragment() {
        if (WeatherData.instance.items.count() != 0) {
                    var mainFragment = MainFragment.create(WeatherData.instance.get(0))
                    supportFragmentManager.beginTransaction()
                        .add(R.id.fragment_container, mainFragment)
                        .commit()
        } else {
            val snackbar = Snackbar.make(
                fragment_container,
                getString(R.string.connection_error),
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction(R.string.retry) {
                    init()
                }

                .setTextColor(resources.getColor(R.color.settingsTextColor))
            snackbar.view.setBackgroundColor(resources.getColor(R.color.white))
            snackbar.show()
        }
    }

    private fun cityDataInit() {
        val locale = SettingsPresenter.instance.locale
        val defaultCityKey = 294021


        //Читаем пользовательские данные с файла
        readUserData(locale)

        //Грузим город по-умолчанию -- потом получать по GPS город
        if (WeatherData.instance.items.count() == 0) {
            loadDefaultCItyData(defaultCityKey, locale)
        }
    }

    private fun loadDefaultCItyData(defaultCityKey: Int, locale: String) {
        var data =  //boundService?.loadWeatherData(defaultCityKey, locale, SettingsPresenter.instance.temperature_metric)
            DataLoader.load(defaultCityKey, locale, SettingsPresenter.instance.temperature_metric)
        if (data != null)
            WeatherData.instance.add(data)
    }

    private fun settingsInit() {
        val settings = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val settingsInstance = SettingsPresenter.instance
        settingsInstance.locale = resources.configuration.locale.toLanguageTag()
        settingsInstance.temperatureMeasureIndex = settings.getInt(
            settingsInstance.APP_PREFERENCES_TEMPERATURE,
            TemperatureMeasureEnum.Metric.ordinal
        )
        settingsInstance.windSpeed = settings.getInt(settingsInstance.APP_PREFERENCES_WIND_SPEED, 0)
    }

    private fun updateCurrentUserData(locale: String) {
        for (item in WeatherData.instance.items) {
            DataLoader.updateCurrentCondition(item, locale)
        }
    }

    private fun readUserData(locale: String) {
        readUserDataFromFile()
        updateCurrentUserData(locale)
    }

    private fun readUserDataFromFile() {
        if (File(filesDir, FILE_NAME).exists()) {
            try {
                val fis = openFileInput(FILE_NAME);
                val iStream = ObjectInputStream(fis);
                val items = iStream.readObject() as ArrayList<CityWeatherForecastData>;
                for (item in items)
                    WeatherData.instance.items.add(item)
                iStream.close();
                fis.close();
            } catch (e: IOException) {
                Log.e(TAG, "Fail to read from file", e);
            }
        }
    }

    private fun saveUserDataToFile(fileName: String) {
        try {
            val fos = openFileOutput(fileName, MODE_PRIVATE)
            val os = ObjectOutputStream(fos);
            os.writeObject(WeatherData.instance.items);
            os.close();
            fos.close();
        } catch (e: Exception) {
            Log.e(TAG, "Fail write file", e);
        }
    }

    override fun onStop() {
        saveUserDataToFile(FILE_NAME)
        super.onStop()
    }

    private fun setThemeDependTime() {
        val hour = Calendar.getInstance().time.hours
        when (hour) {
            in 0..6, in 22..24 -> {
                this.setTheme(R.style.NightTheme)
            }
            else -> {
                setTheme(R.style.DayTheme)
            }
        }

    }

}
