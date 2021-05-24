package org.example.weatherprototype.utils.checkers

import android.text.TextUtils
import android.util.Patterns


object EmailChecker {
    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}