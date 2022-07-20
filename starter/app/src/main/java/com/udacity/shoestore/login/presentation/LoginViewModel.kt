package com.udacity.shoestore.login.presentation

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.utils.USER_LOGGED_IN

class LoginViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    fun setUserLoggedIn() {
        sharedPreferences.edit().putBoolean(USER_LOGGED_IN, true).apply()
    }

}