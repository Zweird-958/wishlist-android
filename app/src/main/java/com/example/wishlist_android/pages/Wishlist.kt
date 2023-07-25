package com.example.wishlist_android.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlist_android.R
import com.example.wishlist_android.api.classes.Wish
import com.example.wishlist_android.components.Drawer
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

    Drawer(
        title = stringResource(R.string.wishlist),
        navController = navController,
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    navController.navigate("addWish")
                },
                modifier = Modifier
                    .padding(16.dp),
            ) {
                Icon(
                    modifier = Modifier.size(26.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_wish),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }) {


        Box(Modifier.pullRefresh(pullRefreshState)) {


            if (wishlistState.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                    ) {
                        Text(
                            text = stringResource(R.string.wishlist_empty),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(32.dp)
                                .fillMaxWidth()
                        )
                    }
                }

            } else {

                LazyColumn {
                    wishlistState.forEach { wish ->
                        item {
                            WishCard(wish = wish)
                        }
                    }
                }
            }

            PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}