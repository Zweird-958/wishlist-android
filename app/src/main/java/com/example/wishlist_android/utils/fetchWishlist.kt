package com.example.wishlist_android.utils

import android.os.Looper
import androidx.navigation.NavController
import com.example.wishlist_android.api.RetrofitHelper
import com.example.wishlist_android.api.WishApi
import com.example.wishlist_android.wishlist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
            wishlist = result
        }

        if (navigateToWishlist) {
            withContext(Dispatchers.Main) {
                navigateAndClearHistory(navController, "wishlist", currentRoute)
            }
        }

    } else {

        Looper.prepare()
        handleErrors(response, navController, currentRoute)
        Looper.loop()

    }
}