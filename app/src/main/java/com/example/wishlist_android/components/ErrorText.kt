package com.example.wishlist_android.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ErrorText(error: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp),
        text = error,
        color = MaterialTheme.colorScheme.error,
        textAlign = TextAlign.Start
    )
}