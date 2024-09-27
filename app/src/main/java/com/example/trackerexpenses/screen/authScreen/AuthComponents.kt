package com.example.trackerexpenses.screen.authScreen

import android.util.Patterns

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidPassword(password: String): Boolean {
    val passwordPattern = "^[A-Z](?=.*[a-z])(?=.*[0-9])(?=.*[!@#\$%^&*])[A-Za-z0-9!@#\$%^&*]{7,}$"
    return Regex(passwordPattern).matches(password)
}