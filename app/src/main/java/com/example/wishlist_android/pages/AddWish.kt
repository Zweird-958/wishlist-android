package com.example.wishlist_android.pages

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.wishlist_android.MainActivity.Companion.wishApi
import com.example.wishlist_android.MainActivity.Companion.wishlist
import com.example.wishlist_android.R
import com.example.wishlist_android.components.BackScaffold
import com.example.wishlist_android.components.WishForm
import com.example.wishlist_android.utils.api
import com.example.wishlist_android.utils.navigateAndClearHistory
import kotlinx.coroutines.launch
import java.io.File


fun getFileFromUri(uri: Uri, context: Context): File {
    Log.d("uri", uri.toString())
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.cacheDir, "temp_image.jpg")

    inputStream?.use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }

    return file
}


@Composable
fun AddWish(navController: NavController) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    BackScaffold(
        navController = navController,
    ) {
        WishForm(
            onSubmit = { name, price, link, currency, image ->
                scope.launch {
                    isLoading = true

                    val response = api(
                        response = {
                            wishApi.createWish(
                                currency = currency,
                                name = name,
                                price = price,
                                image = image,
                                link = link,
                            )
                        },
                        context,
                        navController,
                        "addWish"
                    )

                    val wish = response.result

                    if (wish != null) {
                        wishlist.add(wish)
                        navigateAndClearHistory(navController, "wishlist", "addWish")
                    }

                    isLoading = false
                }
            },
            buttonTitle = stringResource(R.string.add_wish),
            title = stringResource(R.string.add_wish),
            isLoading = isLoading
        ) {}

    }
}