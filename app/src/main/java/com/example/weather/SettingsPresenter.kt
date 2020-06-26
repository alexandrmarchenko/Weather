package com.example.weather

class SettingsPresenter {

    companion object {
        val instance = SettingsPresenter()
    }

    var temperature: String = ""
        get() {
            return field
        }
        set(value) {
            field = value
        }

    var windSpeed: String = ""
        get() {
            return field
        }
        set(value) {
            field = value
        }


}