package com.example.weather

class SettingsPresenter {

    companion object {
        val instance = SettingsPresenter()
    }

    val APP_PREFERENCES_TEMPERATURE = "temperature"
    val APP_PREFERENCES_WIND_SPEED = "windspeed"

    var locale: String = ""
        get() {
            return field
        }
        set(value) {
            field = value
        }

    var temperature_metric: Boolean = true
        get() {
            return field
        }

    var temperatureMeasureIndex: Int = 0
        get() {
            return field
        }
        set(value) {
            field = value
            this.temperature_metric = TemperatureMeasureEnum.Metric.ordinal == value
            this.temperatureMeasureValue = TemperatureMeasureEnum.values()[value].value
        }

    var temperatureMeasureValue: String = ""
        get() {
            return field
        }


    var windSpeed: Int = 0
        get() {
            return field
        }
        set(value) {
            field = value
        }


}