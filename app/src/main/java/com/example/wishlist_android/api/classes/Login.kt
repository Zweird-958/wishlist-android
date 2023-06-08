package com.example.wishlist_android.api.classes

data class LoginResult(
    val result: String? = "",
    val error: String? = ""
)


data class LoginRequest(
    val email: String,
    val password: String
)