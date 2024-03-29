package com.example.wishlist_android.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.wishlist_android.config

fun saveToken(context: Context, token: String?) {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(config.keys.sharedPreferencesKey, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    editor.putString(config.keys.tokenKey, token)
    editor.apply()
}