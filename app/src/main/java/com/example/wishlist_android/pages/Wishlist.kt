package com.example.wishlist_android.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.wishlist_android.R
import com.example.wishlist_android.api.classes.Wish
import com.example.wishlist_android.components.RoundedButton
import com.example.wishlist_android.utils.fetchWishlist
import com.example.wishlist_android.wishlist

@Composable
fun Wishlist(navController: NavController) {
    val wishlistState = remember { mutableStateListOf<Wish>() }

    fun updateWishlist() {
        wishlistState.clear()
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
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                    ) {
                        Row(modifier = Modifier.padding(16.dp)) {

                            Box(
                                modifier = Modifier
                                    .size(height = 130.dp, width = 130.dp)
                                    .clip(shape = RoundedCornerShape(5.dp))
                            ) {

                                AsyncImage(

                                    model = wish.image,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .blur(20.dp),
                                    contentScale = ContentScale.Crop
                                )
                                AsyncImage(

                                    model = wish.image,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(2.dp)
                                        .clip(shape = RoundedCornerShape(5.dp)),
                                )
                            }

                            Column(modifier = Modifier.padding(start = 16.dp)) {
                                Text(text = wish.name)
                                Text(text = wish.currency)
                                Text(text = wish.price.toString())
                                RoundedButton(
                                    buttonTitle = stringResource(R.string.buy),
                                    onSubmit = { println("fsfs") })


                            }

                        }
                    }
                }
            }
        }
    }
}