package org.example.weatherprototype.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.weatherprototype.data.api.WeatherApi
import org.example.weatherprototype.data.model.WeatherResponse
import org.example.weatherprototype.network.NetworkResult
import org.example.weatherprototype.network.connection.NoConnectionException
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi) {
    suspend fun getWeatherForecast(
        latitude: Double,
        longitude: Double
    ): NetworkResult<WeatherResponse> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                NetworkResult.Success(weatherApi.getForecast(latitude, longitude))
            } catch (e: NoConnectionException) {
                NetworkResult.NoConnection(e)
            } catch (e: Exception) {
                NetworkResult.Error(e)
            }
        }
    }
}