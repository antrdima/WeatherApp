package org.example.weatherprototype.ui.authorize

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.example.weatherprototype.utils.Event
import org.example.weatherprototype.utils.checkers.EmailChecker
import org.example.weatherprototype.utils.checkers.PasswordChecker
import org.example.weatherprototype.utils.postEvent
import javax.inject.Inject

@HiltViewModel
class AuthorizeViewModel @Inject constructor() : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun authorizeClick() {
        val emailValue = email.value ?: ""
        val passwordValue = password.value ?: ""

        if (!EmailChecker.isValidEmail(emailValue)) {
            _showInvalidEmailError.postEvent()
            return
        }
        if (!PasswordChecker.isPasswordValid(passwordValue)) {
            _showInvalidPasswordError.postEvent()
            return
        }
        _showLoginSuccess.postEvent()
    }

    fun setLocation(latitude: Double, longitude: Double) {
        Log.d(TAG, "lat = $latitude long= $longitude")
    }

    private val _showInvalidEmailError = MutableLiveData<Event<Unit>>()
    val showInvalidEmailError: LiveData<Event<Unit>> = _showInvalidEmailError

    private val _showInvalidPasswordError = MutableLiveData<Event<Unit>>()
    val showInvalidPasswordError: LiveData<Event<Unit>> = _showInvalidPasswordError

    private val _showLoginSuccess = MutableLiveData<Event<Unit>>()
    val showLoginSuccess: LiveData<Event<Unit>> = _showLoginSuccess

    companion object {
        const val TAG = "AuthorizeViewModel"
    }
}