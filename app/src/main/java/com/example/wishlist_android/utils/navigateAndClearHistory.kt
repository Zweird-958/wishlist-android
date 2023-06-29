package com.example.wishlist_android.utils

import androidx.navigation.NavController

fun navigateAndClearHistory(navController: NavController, route: String, currentRoute: String) {
    navController.navigate(route) {
        popUpTo(currentRoute) {
            inclusive = true
        }
    }
}