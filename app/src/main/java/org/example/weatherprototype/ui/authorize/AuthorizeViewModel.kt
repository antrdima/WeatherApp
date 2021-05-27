package org.example.weatherprototype.ui.authorize

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.example.weatherprototype.R
import org.example.weatherprototype.data.model.WeatherResponse
import org.example.weatherprototype.data.repository.WeatherRepository
import org.example.weatherprototype.network.NetworkResult
import org.example.weatherprototype.ui.base.BaseViewModel
import org.example.weatherprototype.utils.Event
import org.example.weatherprototype.utils.WeatherUtils
import org.example.weatherprototype.utils.checkers.EmailChecker
import org.example.weatherprototype.utils.checkers.PasswordChecker
import org.example.weatherprototype.utils.extensions.postEvent
import javax.inject.Inject

@HiltViewModel
class AuthorizeViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    BaseViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    var latitude = 55.45
    var longitude = 37.36
    var isDefaultLocation = true

    fun authorizeClick() {
        val emailValue = email.value ?: ""
        val passwordValue = password.value ?: ""

        if (!EmailChecker.isValidEmail(emailValue)) {
            showSnackbarMessage(R.string.msg_email_invalid)
            return
        }
        if (!PasswordChecker.isPasswordValid(passwordValue)) {
            _showInvalidPasswordError.postEvent()
            return
        }
        sendWeatherRequest()
    }

    fun setLocation(latitude: Double, longitude: Double) {
        isDefaultLocation = false
        this.latitude = latitude
        this.longitude = longitude
    }

    private fun sendWeatherRequest() {
        viewModelScope.launch {
            when (val response = weatherRepository.getWeatherForecast(latitude, longitude)) {
                is NetworkResult.Success -> showWeatherForecastSnackbar(response.data)
                is NetworkResult.NoConnection -> showNoConnectionError()
                is NetworkResult.Error -> showWeatherApiError()
            }
        }
    }

    private fun showWeatherForecastSnackbar(data: WeatherResponse) {
        val tempInCelsius = WeatherUtils.formatTemperature(data.main.temp)
        if (isDefaultLocation) showDefaultLocationWeather(tempInCelsius)
        else showCurrentLocationWeather(tempInCelsius)
    }

    private fun showDefaultLocationWeather(tempInCelsius: String) {
        showSnackbarMessage("Погода в месте по умолчанию (Москва) : $tempInCelsius \u2103")
    }

    private fun showCurrentLocationWeather(tempInCelsius: String) {
        showSnackbarMessage("Погода в текущем месте : $tempInCelsius \u2103")
    }

    private fun showWeatherApiError() {
        showSnackbarMessage(R.string.weather_api_error)
    }

    private val _showInvalidPasswordError = MutableLiveData<Event<Unit>>()
    val showInvalidPasswordError: LiveData<Event<Unit>> = _showInvalidPasswordError

    companion object {
        const val TAG = "AuthorizeViewModel"
    }
}