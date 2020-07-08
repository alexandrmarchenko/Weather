package com.example.weather

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setBackBtnListener()

        setTemperatureListener()

        setWindSpeedListener()

    }

    private fun setWindSpeedListener() {
        windSpeed.setOnClickListener {
            val windSpeedArray = resources.getStringArray(R.array.wind_speed_measure)
            val checkedItem = windSpeedArray.indexOf(windSpeedVal.text)
            val builder = MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.wind_speed))
                .setSingleChoiceItems(
                    R.array.wind_speed_measure,
                    checkedItem,
                    object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            windSpeedVal.text = windSpeedArray[which]

                            saveWindSpeed()
                            dialog?.dismiss()
                        }

                    })
            builder.show()
        }
    }

    private fun setTemperatureListener() {
        temperature.setOnClickListener {
            val tempArray = resources.getStringArray(R.array.temperature_measure)
            val checkedItem = tempArray.indexOf(temperatureVal.text)
            val builder = MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.temperature))
                .setSingleChoiceItems(
                    R.array.temperature_measure,
                    checkedItem,
                    object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            temperatureVal.text = tempArray[which]

                            saveTemperature()
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

        temperatureVal.text = SettingsPresenter.instance.temperature
        windSpeedVal.text = SettingsPresenter.instance.windSpeed
    }

    private fun saveTemperature() {
        SettingsPresenter.instance.temperature = temperatureVal.text.toString()
    }

    private fun saveWindSpeed() {
        SettingsPresenter.instance.windSpeed = windSpeedVal.text.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveTemperature()
        saveWindSpeed()
    }
}