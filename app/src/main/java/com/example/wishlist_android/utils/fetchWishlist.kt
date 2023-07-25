package com.example.wishlist_android.utils

import androidx.navigation.NavController
import com.example.wishlist_android.api.RetrofitHelper
import com.example.wishlist_android.api.WishApi
import com.example.wishlist_android.api.classes.Wish
import com.example.wishlist_android.wishlist

suspend fun fetchWishlist(
    navController: NavController,
    currentRoute: String,
    navigateToWishlist: Boolean = false
) {
    val wishApi = RetrofitHelper.getInstance().create(WishApi::class.java)
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