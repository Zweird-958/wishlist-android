package com.example.wishlist_android.utils

import androidx.navigation.NavController
import com.example.wishlist_android.MainActivity.Companion.wishApi
import com.example.wishlist_android.MainActivity.Companion.wishlist
import com.example.wishlist_android.classes.Wish

suspend fun fetchWishlist(
    navController: NavController,
    currentRoute: String,
    navigateToWishlist: Boolean = false
) {

    val response = api(
        response = { wishApi.getWish() },
        context = navController.context,
        navController = navController,
        goToRetry = true,
        currentRoute = "loading"
    )

    val result = response.result

    if (result != null) {
        wishlist = result as MutableList<Wish>
    }

    if (navigateToWishlist) {
        navigateAndClearHistory(navController, "wishlist", currentRoute)
    }

}