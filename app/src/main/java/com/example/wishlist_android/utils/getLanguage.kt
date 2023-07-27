package com.example.wishlist_android.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.wishlist_android.config

fun getLanguage(context: Context): String? {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(config.keys.sharedPreferencesKey, Context.MODE_PRIVATE)
    return sharedPreferences.getString(config.keys.langKey, null)
}