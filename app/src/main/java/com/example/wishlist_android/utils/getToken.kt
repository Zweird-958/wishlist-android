package com.example.wishlist_android.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.wishlist_android.config

fun getToken(context: Context): String? {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(config.session.sharedPreferencesKey, Context.MODE_PRIVATE)
    return sharedPreferences.getString(config.session.tokenKey, null)
}