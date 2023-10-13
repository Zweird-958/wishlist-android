package com.example.wishlist_android.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlist_android.MainActivity.Companion.currencies
import com.example.wishlist_android.MainActivity.Companion.wishApi
import com.example.wishlist_android.utils.api
import com.example.wishlist_android.utils.fetchWishlist
import com.example.wishlist_android.utils.getToken
import com.example.wishlist_android.utils.navigateAndClearHistory
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoadingPage(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val tokenLoaded = getToken(context = context)

        scope.launch {
            val response = api(
                response = { wishApi.getCurrencies() },
                context = context,
                navController = navController,
                goToRetry = true,
                currentRoute = "loading"
            )

            val currenciesResult = response.result
            if (currenciesResult != null) {
                currencies = currenciesResult
            }

            if (tokenLoaded != null) {
                fetchWishlist(navController, "loading", true)
            } else {
                navigateAndClearHistory(navController, "signIn", "loading")
            }

        }
    }


    Scaffold(
        content = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.primary,

                    )
            }
        }
    )
}