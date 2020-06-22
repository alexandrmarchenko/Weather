package com.example.weather

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class CityManagement : AppCompatActivity() {

    private lateinit var cities: Array<String>
    private lateinit var cityList: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_management)

        cities = resources.getStringArray(R.array.cities)
        cityList = findViewById<LinearLayout>(R.id.cityList)

        this.fillCityList(cities);


        val back = findViewById<Button>(R.id.back)
        back.setOnClickListener(View.OnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        })

        val cityEditText = findViewById<EditText>(R.id.enter_city)
        cityEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val cList = cities.filter { city ->
                    city.toUpperCase().contains(s.toString().toUpperCase())
                }
                fillCityList(cList.toTypedArray());
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

    }

    private fun fillCityList(cities: Array<String>) {
        cityList.removeAllViews()
        for (city in cities) {
            val cityTextView = TextView(this)
            cityTextView.setBackgroundColor(resources.getColor(R.color.cityTextViewColor))
            val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            layoutParams.setMargins(0, 5, 0, 5)
            cityTextView.layoutParams = layoutParams

            cityTextView.setOnClickListener(View.OnClickListener {
                val data = intent
                data.putExtra(MainActivity.CITY_KEY, city)
                setResult(Activity.RESULT_OK, data)
                finish()
            })

            cityTextView.text = city
            cityList.addView(cityTextView)
        }
    }
}
