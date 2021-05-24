package org.example.weatherprototype.utils

import androidx.lifecycle.MutableLiveData

fun MutableLiveData<Event<Unit>>.postEvent() {
    this.postValue(Event(Unit))
}

fun <T> MutableLiveData<Event<T>>.postEvent(arg: T) {
    this.postValue(Event(arg))
}
