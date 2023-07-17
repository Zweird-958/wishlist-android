package com.example.wishlist_android.api.classes

data class SignUpResponse(
    val result: SignUpResult?,
    val error: String?
)

data class SignUpResult(
    val id: Int,
    val email: String,
    val passwordHash: String,
)