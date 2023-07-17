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

    LaunchedEffect(Unit) {
        val tokenLoaded = getToken(context = context)

        if (tokenLoaded != null) {

            token = tokenLoaded

            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    try {
                        fetchWishlist(navController, "loading", true)
                    } catch (e: Exception) {
                        handleErrors(e, navController, "signIn", context)
                    }
                }
            }
        } else {
            navigateAndClearHistory(navController, "signIn", "loading")
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