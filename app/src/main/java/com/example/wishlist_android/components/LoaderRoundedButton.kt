package com.example.wishlist_android.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoaderRoundedButton(
    modifier: Modifier = Modifier,
    loading: Boolean,
    onSubmit: () -> Unit,
    isEnabled: Boolean = true,
    children: @Composable () -> Unit
) {
    RoundedButton(
        modifier = modifier,
        onSubmit = { onSubmit() },
        isEnabled = isEnabled,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary,

                    )
            }
            children()
        }
    }
}