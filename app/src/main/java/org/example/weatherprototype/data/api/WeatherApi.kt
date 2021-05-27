package org.example.weatherprototype.data.api

import org.example.weatherprototype.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getForecast(@Query("lat") lat: Double, @Query("lon") lon: Double): WeatherResponse
}