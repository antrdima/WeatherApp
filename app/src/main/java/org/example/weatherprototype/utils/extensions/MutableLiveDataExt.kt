package org.example.weatherprototype.utils.extensions

import androidx.lifecycle.MutableLiveData
import org.example.weatherprototype.utils.Event

fun MutableLiveData<Event<Unit>>.postEvent() {
    this.postValue(Event(Unit))
}

fun <T> MutableLiveData<Event<T>>.postEvent(arg: T) {
    this.postValue(Event(arg))
}
