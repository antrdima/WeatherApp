package org.example.weatherprototype.utils.checkers

import java.util.regex.Pattern

object PasswordChecker {
    private val upperRegex = Pattern.compile(".*[A-Z].*")
    private val lowerRegex = Pattern.compile(".*[a-z].*")
    private val digitRegex = Pattern.compile(".*\\d.*")

    fun isPasswordValid(password: String): Boolean {
        return (password.length >= 6
                && upperRegex.matcher(password).matches()
                && lowerRegex.matcher(password).matches()
                && digitRegex.matcher(password).matches())
    }
}