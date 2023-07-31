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
import com.example.wishlist_android.api.RetrofitHelper
import com.example.wishlist_android.api.WishApi
import com.example.wishlist_android.currencies
import com.example.wishlist_android.token
import com.example.wishlist_android.utils.fetchWishlist
import com.example.wishlist_android.utils.getToken
import com.example.wishlist_android.utils.handleErrors
import com.example.wishlist_android.utils.navigateAndClearHistory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun LoadingPage(navController: NavController) {
    val context = LocalContext.current
    val wishApi = RetrofitHelper.getInstance().create(WishApi::class.java)

    LaunchedEffect(Unit) {
        val tokenLoaded = getToken(context = context)

        if (tokenLoaded != null) {

            token = tokenLoaded

            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    try {
                        fetchWishlist(navController, "loading", true)
                    } catch (e: Exception) {
                        handleErrors(e, navController, context, goToRetry = true)
                    }
                }
            }
        } else {
            navigateAndClearHistory(navController, "signIn", "loading")
        }

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                try {
                    val response = wishApi.getCurrencies()
                    if (response.isSuccessful) {
                        val currenciesResult = response.body()?.result
                        if (currenciesResult != null) {
                            currencies = currenciesResult
                        }
                    } else {
                        handleErrors(
                            response,
                            navController,
                            "loading"
                        )
                    }
                } catch (e: Exception) {
                    handleErrors(e, navController, context, goToRetry = true)
                }
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