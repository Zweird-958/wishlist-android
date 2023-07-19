package com.example.wishlist_android.pages

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.wishlist_android.R
import com.example.wishlist_android.components.Drawer

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Profile(navController: NavController) {
    Drawer(title = stringResource(R.string.profile), navController = navController) {
        Text(text = "profile")
    }
}