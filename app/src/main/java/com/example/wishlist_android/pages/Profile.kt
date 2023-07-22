package com.example.wishlist_android.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlist_android.R
import com.example.wishlist_android.components.Drawer
import com.example.wishlist_android.components.RoundedButton
import com.example.wishlist_android.token
import com.example.wishlist_android.utils.navigateAndClearHistory
import com.example.wishlist_android.utils.saveToken

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Profile(navController: NavController) {
    Drawer(title = stringResource(R.string.profile), navController = navController) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.width(300.dp)) {
                RoundedButton(onSubmit = {

                    navigateAndClearHistory(navController, "signIn", "profile")
                    token = ""
                    saveToken(navController.context, null)
                }) {
                    Text(text = stringResource(R.string.sign_out))
                }
            }


        }
    }
}