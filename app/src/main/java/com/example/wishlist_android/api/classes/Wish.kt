package com.example.wishlist_android.api.classes

data class Wish(

    val name: String,
    val image: String,
    val currency: String,
    val price: Float,
    val priceFormatted: String,
    val link: String?,
    val purchased: Boolean,
    val id: Int,
    val createdAt: String,

    )

data class WishResult(
    val result: List<Wish>? = null,
    val error: String? = ""
)