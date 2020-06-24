package com.example.weather

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val CITY_KEY = "CITY"
    }

    private val REQUEST_GET_DATA_TYPE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setCityManagementBtnListener()

        setSettingsPopupBtnListener()
    }

    private fun setSettingsPopupBtnListener() {
        settingsPopup.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                showSettingsPopup(v)
            }
        })
    }

    private fun setCityManagementBtnListener() {
        cityManagement.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                callCityManagement()
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
