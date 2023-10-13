package com.example.wishlist_android.pages

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlist_android.MainActivity.Companion.wishApi
import com.example.wishlist_android.MainActivity.Companion.wishlist
import com.example.wishlist_android.R
import com.example.wishlist_android.api.classes.Wish
import com.example.wishlist_android.components.Drawer
import com.example.wishlist_android.components.Dropdown
import com.example.wishlist_android.components.PopUpFullSize
import com.example.wishlist_android.components.WishCard
import com.example.wishlist_android.components.WishSwipeableCard
import com.example.wishlist_android.utils.api
import com.example.wishlist_android.utils.fetchWishlist
import com.example.wishlist_android.utils.handleErrors
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Wishlist(navController: NavController, userId: Int?) {

    val sortOptions = listOf(
        stringResource(R.string.date),
        stringResource(R.string.ascending_price),
        stringResource(R.string.descending_price),
    )
    val filterOptions = listOf(
        stringResource(R.string.all),
        stringResource(R.string.bought),
        stringResource(R.string.not_bought),
    )

    val context = LocalContext.current

    val wishlistState = remember { mutableStateListOf<Wish>() }
    var refreshing by remember { mutableStateOf(false) }
    val isPopupVisible = remember { mutableStateOf(false) }

    val deleteLoading = remember { mutableStateOf(false) }
    val selectedWish: MutableState<Wish?> = remember { mutableStateOf(null) }
    val popupScale = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    val selectedSort = remember { mutableStateOf(sortOptions[0]) }
    val selectedFilter = remember { mutableStateOf(filterOptions[0]) }

    val title = remember { mutableStateOf(context.getString(R.string.wishlist)) }

    fun sortWishlist(sort: String) {
        when (sort) {
            sortOptions[0] -> wishlistState.sortBy { it.createdAt }
            sortOptions[1] -> wishlistState.sortBy { it.price }
            sortOptions[2] -> wishlistState.sortByDescending { it.price }
        }
    }

    fun filterWishlist(filter: String, currentWishlist: List<Wish> = wishlist) {
        wishlistState.clear()
        when (filter) {
            filterOptions[0] -> wishlistState.addAll(currentWishlist)
            filterOptions[1] -> wishlistState.addAll(currentWishlist.filter { it.purchased })
            filterOptions[2] -> wishlistState.addAll(currentWishlist.filter { !it.purchased })
        }
    }


    suspend fun deleteWish() {
        if (selectedWish.value == null) {
            return
        }

        try {
            deleteLoading.value = true
            val response =
                wishApi.deleteWish(selectedWish.value!!.id)
            if (response.isSuccessful) {
                wishlist.remove(selectedWish.value!!)
                filterWishlist(selectedFilter.value)
            } else {
                handleErrors(response, navController, "wishlist")
            }
        } catch (e: Exception) {
            handleErrors(
                e,
                navController,
                context = navController.context
            )
        } finally {
            deleteLoading.value = false
            popupScale.animateTo(0f)
            isPopupVisible.value = false
        }

    }

//    Shared Wishlist

    if (userId != null) {

        LaunchedEffect(refreshing) {
            val response = api(
                response = wishApi.getSharedWish(userId),
                context = context,
                navController = navController,
                currentRoute = "wishlist"
            )
            if (response.result != null) {
                filterWishlist(selectedFilter.value, response.result)
            }

            val users = api(
                response = wishApi.getSharedUsers(),
                context = context,
                navController = navController,
                currentRoute = "wishlist"
            )

            if (users.result != null) {
                val user = users.result.find { it.id == userId }
                if (user != null) {
                    title.value =
                        context.getString(R.string.wishlist_shared_title, user.username)
                }
            }


        }
        refreshing = false
        sortWishlist(selectedSort.value)
    }
//    User Wishlist
    else {
        LaunchedEffect(wishlist, refreshing) {
            if (wishlist.isEmpty() || refreshing) {
                try {
                    fetchWishlist(navController, "wishlist")
                } catch (e: Exception) {
                    handleErrors(e, navController, context, goToRetry = true)
                }
            }
            refreshing = false
            filterWishlist(selectedFilter.value)
            sortWishlist(selectedSort.value)
        }
    }

    val pullRefreshState = rememberPullRefreshState(refreshing, { refreshing = true })

    Drawer(
        title = title.value,
        navController = navController,
        floatingActionButton = {
            if (userId == null) {
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
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }) {

        if (isPopupVisible.value) {
            PopUpFullSize(
                popupScale = popupScale,
                isPopupVisible = isPopupVisible,
                isLoading = deleteLoading,
                textContent = stringResource(R.string.delete_pop_up_text)
            ) {
                scope.launch {
                    deleteWish()
                }
            }
        }

        Box(Modifier.pullRefresh(pullRefreshState)) {

            if (wishlistState.isEmpty() && selectedFilter.value == filterOptions[0]) {
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
                            text = if (userId == null) stringResource(R.string.wishlist_empty) else stringResource(
                                R.string.this_wishlist_is_empty
                            ),
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
                    item {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Dropdown(
                                selectedChoice = selectedSort,
                                choices = sortOptions,
                                onClick = { sort -> sortWishlist(sort) },
                                width = 170
                            )
                            Dropdown(
                                selectedChoice = selectedFilter,
                                choices = filterOptions,
                                onClick = { filter -> filterWishlist(filter) },
                                width = 170
                            )
                        }

                    }

                    items(wishlistState, key = { it.id }) { wish ->
                        if (userId == null) {
                            WishSwipeableCard(wish = wish, navController, onClick = {
                                scope.launch {
                                    isPopupVisible.value = true
                                    selectedWish.value = wish
                                    popupScale.animateTo(1f, animationSpec = tween(300))
                                }
                            })
                        } else {
                            WishCard(
                                modifier = Modifier.padding(
                                    horizontal = 16.dp,
                                    vertical = 8.dp
                                ),
                                wish = wish
                            )
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter),
                backgroundColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}