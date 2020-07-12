package com.example.weather

import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.*


class SettingsActivity : AppCompatActivity() {

    private lateinit var settings: SharedPreferences

    private lateinit var windSpeedArray: Array<String>
    private var tempArray = ArrayList<String>()

    private var temperatureValue = 0
    private var windSpeedValue = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        init()

        setBackBtnListener()

        setTemperatureListener()

        setWindSpeedListener()

    }

    private fun init() {
        settings = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        windSpeedArray = resources.getStringArray(R.array.wind_speed_measure)
        for (item in TemperatureMeasureEnum.values()) {
            tempArray.add(item.value)
        }

        val settingsInstance = SettingsPresenter.instance

        temperatureValue = settingsInstance.temperatureMeasureIndex
        windSpeedValue = settingsInstance.windSpeed

        temperatureVal.text = tempArray[temperatureValue]
        windSpeedVal.text = windSpeedArray[windSpeedValue]

    }

    private fun setWindSpeedListener() {
        windSpeed.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.wind_speed))
                .setSingleChoiceItems(
                    R.array.wind_speed_measure,
                    windSpeedValue,
                    object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            windSpeedVal.text = windSpeedArray[which]

                            saveWindSpeed(which)
                            dialog?.dismiss()
                        }

                    })
            builder.show()
        }
    }

    private fun setTemperatureListener() {
        temperature.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.temperature))
                .setSingleChoiceItems(
                    R.array.temperature_measure,
                    temperatureValue,
                    object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            temperatureVal.text = tempArray[which]

                            saveTemperature(which)
                            dialog?.dismiss()
                        }
                    })
            builder.show()
        }
    }

    private fun setBackBtnListener() {
        topAppBar.setNavigationOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()

        init()
    }

    private fun saveTemperature(index: Int) {
        SettingsPresenter.instance.temperatureMeasureIndex = index
        temperatureValue = index
        val editor = settings.edit()
        editor.putInt(SettingsPresenter.instance.APP_PREFERENCES_TEMPERATURE, index)
        editor.apply()
    }

    private fun saveWindSpeed(index: Int) {
        SettingsPresenter.instance.windSpeed = index
        windSpeedValue = index
        val editor = settings.edit()
        editor.putInt(SettingsPresenter.instance.APP_PREFERENCES_WIND_SPEED, index)
        editor.apply()
    }

}