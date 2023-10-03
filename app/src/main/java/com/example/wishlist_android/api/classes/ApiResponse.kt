package com.example.wishlist_android.api.classes

data class ApiResponse<T>(
    val result: T? = null,
    val error: String = ""
)