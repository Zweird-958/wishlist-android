package com.example.wishlist_android.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.wishlist_android.R

@Composable
fun BackScaffold(navController: NavController, children: @Composable () -> Unit) {
    CustomScaffold(
        title = "",
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    Icons.Default.ArrowBackIos,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        },
    ) {
        children()
    }
}