package com.example.wishlist_android.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.wishlist_android.api.classes.Wish
import com.example.wishlist_android.components.WishCard
import com.example.wishlist_android.utils.fetchWishlist
import com.example.wishlist_android.wishlist

@Composable
fun Wishlist(navController: NavController) {
    val wishlistState = remember { mutableStateListOf<Wish>() }

    fun updateWishlist() {
        wishlistState.clear()
        wishlistState.addAll(wishlist)
        wishlistState.addAll(wishlist)
    }

    LaunchedEffect(wishlist) {
        if (wishlistState.isEmpty() && wishlist.isNotEmpty()) {
            updateWishlist()
        } else if (wishlist.isEmpty()) {
            fetchWishlist(navController, "wishlist")
            updateWishlist()
        }
    }

    Column {
        LazyColumn {
            wishlistState.forEach { wish ->
                item {
                    WishCard(wish = wish)
                }
            }
        }
    }
}