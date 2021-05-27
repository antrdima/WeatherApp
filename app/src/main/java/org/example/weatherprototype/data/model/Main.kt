package org.example.weatherprototype.data.model

import com.squareup.moshi.Json

data class Main(
    @Json(name = "grnd_level")
    val grndLevel: Int,
    @Json(name = "humidity")
    val humidity: Int,
    @Json(name = "pressure")
    val pressure: Int,
    @Json(name = "sea_level")
    val seaLevel: Int,
    @Json(name = "temp")
    val temp: Double,
    @Json(name = "temp_kf")
    val tempKf: Double,
    @Json(name = "temp_max")
    val tempMax: Double,
    @Json(name = "temp_min")
    val tempMin: Double
)