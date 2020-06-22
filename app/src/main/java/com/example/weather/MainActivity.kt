package com.example.weather

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    companion object {
        const val CITY_KEY = "CITY"
    }

    private val REQUEST_GET_DATA_TYPE = 1

    private lateinit var city: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        city = findViewById<TextView>(R.id.city)
        val cityManActivity = findViewById<Button>(R.id.cityManagement)
        cityManActivity.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                callCityManagement()
            }
        })

        val settingsPopupMenu = findViewById<Button>(R.id.settingsPopup)
        settingsPopupMenu.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                showSettingsPopup(v)
            }
        })

    }

    fun showSettingsPopup(view: View?) {
        val wrapper = ContextThemeWrapper(this, R.style.PopupMenu)
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
                        Toast.makeText(baseContext, item.title, Toast.LENGTH_SHORT).show()
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
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun callCityManagement() {
        val intent = Intent(this, CityManagement::class.java)
        startActivityForResult(intent, REQUEST_GET_DATA_TYPE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_GET_DATA_TYPE) {
            if (resultCode == Activity.RESULT_OK) {
                city.text = data?.extras?.getString(CITY_KEY)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
