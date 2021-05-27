package org.example.weatherprototype.utils

object WeatherUtils {
    fun formatTemperature(temperatureInKelvin: Double): String {
        val temperatureCelsius = convertKelvinToCelsius(temperatureInKelvin)
        return String.format("%.2f", temperatureCelsius)
    }

    private fun convertKelvinToCelsius(temperature: Double): Double {
        return temperature - ABSOLUTE_ZERO
    }

    private const val ABSOLUTE_ZERO = 273.15
}
