package com.example.wishlist_android.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.wishlist_android.api.classes.Wish
import com.example.wishlist_android.components.WishCard
import com.example.wishlist_android.utils.fetchWishlist
import com.example.wishlist_android.wishlist
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun Wishlist(navController: NavController) {
    val wishlistState = remember { mutableStateListOf<Wish>() }
    var refreshing by remember { mutableStateOf(false) }

    fun updateWishlist() {
        wishlistState.clear()
        wishlistState.addAll(wishlist)
    }



    LaunchedEffect(wishlist, refreshing) {
        if (wishlistState.size != wishlist.size) {
            updateWishlist()
        } else if (wishlist.isEmpty() || refreshing) {
            fetchWishlist(navController, "wishlist")
            updateWishlist()
        }
        refreshing = false
    }



    SwipeRefresh(
        state = rememberSwipeRefreshState(refreshing),
        onRefresh = { refreshing = true },
    ) {
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
}