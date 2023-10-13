package com.example.wishlist_android.classes

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