package com.example.wishlist_android.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlist_android.utils.navigateAndClearHistory

@Composable
fun UserBottomRedirection(
    leftText: String,
    rightText: String,
    navController: NavController,
    currentRoute: String
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val offsetY = screenHeight - 32.dp

    Row(
        modifier = Modifier
            .absoluteOffset(y = offsetY)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$leftText ",
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = rightText,
            modifier = Modifier.clickable {
                navigateAndClearHistory(
                    navController,
                    if (currentRoute == "signIn") "signUp" else "signIn",
                    currentRoute
                )
            },
            color = MaterialTheme.colorScheme.primary
        )
    }
}