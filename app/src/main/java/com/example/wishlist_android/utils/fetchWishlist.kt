package com.example.wishlist_android.utils

import androidx.navigation.NavController
import com.example.wishlist_android.MainActivity
import com.example.wishlist_android.MainActivity.Companion.wishlist
import com.example.wishlist_android.api.classes.Wish

suspend fun fetchWishlist(
    navController: NavController,
    currentRoute: String,
    navigateToWishlist: Boolean = false
) {
    val wishApi = MainActivity.wishApi
    val response = wishApi.getWish()

    if (response.isSuccessful) {
        val result = response.body()?.result

        if (result != null) {
            wishlist = result as MutableList<Wish>
        }

        if (navigateToWishlist) {
            navigateAndClearHistory(navController, "wishlist", currentRoute)
        }

    } else {
        handleErrors(response, navController, currentRoute)
    }
}