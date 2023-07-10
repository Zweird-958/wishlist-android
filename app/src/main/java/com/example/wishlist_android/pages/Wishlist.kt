package com.example.wishlist_android.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.wishlist_android.api.classes.Wish
import com.example.wishlist_android.components.WishCard
import com.example.wishlist_android.utils.fetchWishlist
import com.example.wishlist_android.wishlist

@OptIn(ExperimentalMaterialApi::class)
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

    val pullRefreshState = rememberPullRefreshState(refreshing, { refreshing = true })

    Box(Modifier.pullRefresh(pullRefreshState)) {
        LazyColumn {
            wishlistState.forEach { wish ->
                item {
                    WishCard(wish = wish)
                }
            }
        }

        PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}