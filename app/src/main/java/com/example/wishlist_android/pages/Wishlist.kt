package com.example.wishlist_android.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.wishlist_android.wishlist

@Composable
fun Wishlist(navController: NavController) {
    LaunchedEffect(wishlist) {
        wishlist.forEach { item ->
            Log.d("item", item.toString())
        }
    }




    Column {
        LazyColumn {
            wishlist.forEach { wish ->
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                    ) {
                        Row(modifier = Modifier.padding(16.dp)) {

                            AsyncImage(

                                model = wish.image,
                                contentDescription = null,
                                modifier = Modifier.size(height = 160.dp, width = 130.dp)
                                    .background(color = Color.Red),
                            )
                            Column {
                                Text(text = wish.name)
                                Text(text = wish.price.toString())

                            }

                        }
                    }
                }
            }
        }
    }
}