package com.example.wishlist_android.api.classes

data class User(
    val id: Int,
    val username: String,
)

data class UsersResult(
    val result: List<User>? = null,
    val error: String? = ""
)