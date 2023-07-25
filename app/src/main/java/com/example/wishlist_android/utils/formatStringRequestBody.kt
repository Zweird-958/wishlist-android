package com.example.wishlist_android.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun formatStringRequestBody(string: String): RequestBody {
    return string.toRequestBody("text/plain".toMediaTypeOrNull())
}