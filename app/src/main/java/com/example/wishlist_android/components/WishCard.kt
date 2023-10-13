package com.example.wishlist_android.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.wishlist_android.R
import com.example.wishlist_android.classes.Wish
import com.example.wishlist_android.utils.openWebPage

@Composable
fun WishCard(modifier: Modifier = Modifier, wish: Wish) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .height(IntrinsicSize.Min)
        ) {

            WishImage(image = wish.image)

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = wish.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = wish.priceFormatted)

                if (wish.link != null) {
                    RoundedButton(
                        onSubmit = { openWebPage(context, wish.link) },
                    ) {
                        Text(stringResource(R.string.buy))
                    }
                } else {
                    Box(modifier = Modifier.fillMaxWidth())
                }


            }


        }
    }
}