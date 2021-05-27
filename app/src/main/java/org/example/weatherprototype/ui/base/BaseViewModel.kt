package org.example.weatherprototype.ui.base

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.example.weatherprototype.R
import org.example.weatherprototype.utils.Event
import org.example.weatherprototype.utils.extensions.postEvent

abstract class BaseViewModel : ViewModel() {
    protected fun showNoConnectionError() {
        showSnackbarMessage(R.string.error_no_connection)
    }

    protected fun showSnackbarMessage(@StringRes messageId: Int) {
        _showSnackbarMessageId.postEvent(messageId)
    }

    protected fun showSnackbarMessage(message: String) {
        _showSnackbarMessage.postEvent(message)
    }

    private val _showSnackbarMessageId = MutableLiveData<Event<Int>>()
    val showSnackbarMessageId: LiveData<Event<Int>> = _showSnackbarMessageId

    private val _showSnackbarMessage = MutableLiveData<Event<String>>()
    val showSnackbarMessage: LiveData<Event<String>> = _showSnackbarMessage
}