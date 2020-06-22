package com.example.weather

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val back = findViewById<Button>(R.id.back)
        back.setOnClickListener(View.OnClickListener {
            finish()
        })

        val temperature = findViewById<LinearLayout>(R.id.temperature)
        temperature.setOnClickListener(View.OnClickListener {

            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle(resources.getString(R.string.temperature))
                .setItems(R.array.temperature_measure, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val tempArray = resources.getStringArray(R.array.temperature_measure)
                        findViewById<TextView>(R.id.temperatureVal).text = tempArray[which]
                    }

                })
            builder.show()
        })

        val windSpeed = findViewById<LinearLayout>(R.id.windSpeed)
        windSpeed.setOnClickListener(View.OnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle(resources.getString(R.string.wind_speed))
                .setItems(R.array.wind_speed_measure, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val tempArray = resources.getStringArray(R.array.wind_speed_measure)
                        findViewById<TextView>(R.id.windSpeedVal).text = tempArray[which]
                    }

                })
            builder.show()
        })
    }
}

fun showDialog(context: Context) {
    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    builder.setTitle("123")
        .setItems(R.array.temperature_measure, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {

            }

        })
    builder.show()
}

