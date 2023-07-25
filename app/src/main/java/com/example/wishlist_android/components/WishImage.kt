package com.example.wishlist_android.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun WishImage(image: Any?) {
    Box(
        modifier = Modifier
            .size(height = 130.dp, width = 130.dp)
            .clip(shape = RoundedCornerShape(5.dp))
    ) {

        AsyncImage(

            model = image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .blur(20.dp),
            contentScale = ContentScale.Crop
        )
        AsyncImage(

            model = image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
                .clip(shape = RoundedCornerShape(5.dp)),
        )
    }
}