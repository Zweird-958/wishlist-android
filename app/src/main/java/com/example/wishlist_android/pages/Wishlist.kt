package com.example.wishlist_android.pages

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {
                        Text(text = wish.name)
                    }
                }
            }
        }
    }
}