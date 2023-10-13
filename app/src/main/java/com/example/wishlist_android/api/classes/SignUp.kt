package com.example.wishlist_android.api.classes

data class SignUpResult(
    val id: Int,
    val username: String,
    val email: String,
    val passwordHash: String,
)