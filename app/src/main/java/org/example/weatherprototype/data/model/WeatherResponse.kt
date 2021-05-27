package org.example.weatherprototype.data.model

import com.squareup.moshi.Json

data class WeatherResponse(@Json(name = "main") val main: Main)

