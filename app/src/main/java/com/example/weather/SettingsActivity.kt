package com.example.weather

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle(resources.getString(R.string.wind_speed))
                .setItems(R.array.wind_speed_measure, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val tempArray = resources.getStringArray(R.array.wind_speed_measure)
                        windSpeedVal.text = tempArray[which]

                        saveWindSpeed()
                    }

                })
            builder.show()
        }
    }

    private fun setTemperatureListener() {
        temperature.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle(resources.getString(R.string.temperature))
                .setItems(R.array.temperature_measure, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val tempArray = resources.getStringArray(R.array.temperature_measure)
                        temperatureVal.text = tempArray[which]

                        saveTemperature()
                    }
                })
            builder.show()
        }
    }

    private fun setBackBtnListener() {
        back.setOnClickListener{ finish() }
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