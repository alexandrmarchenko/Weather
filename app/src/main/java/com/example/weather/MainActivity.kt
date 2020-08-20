package com.example.weather

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.weather.cityWeatherForecast.CityWeatherForecastData
import com.example.weather.geo.Geo
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
    private val networkReceiver = NetworkReceiver()
    private val batteryReceiver = BatteryReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setThemeDependTime()
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {

            init()

            initNotificationChannel()
            initReceivers()
        }
    }

    private fun init() {

        settingsInit()

        cityDataInit()

        callFragment()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == Geo.PERMISSION_REQUEST_CODE) {
            if (grantResults.size == 2 &&
                (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)
            ) {
                Geo.getInstance(this).requestLocation()
            }
        }
    }

    private fun initReceivers() {
        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_LOW))
        registerReceiver(networkReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(batteryReceiver)
        unregisterReceiver(networkReceiver)
    }

    private fun initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel("2", "name", importance)
            notificationManager.createNotificationChannel(channel)
        }
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
            val pos = Geo.getInstance(this).currentPosition
            if (pos != null) {
                val data =
                    DataLoader.load(pos, locale, SettingsPresenter.instance.temperature_metric)
                if (data != null)
                    WeatherData.instance.add(data)
            } else {
                loadDefaultCItyData(defaultCityKey, locale)
            }
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
