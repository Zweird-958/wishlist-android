package com.example.wishlist_android.pages

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.wishlist_android.MainActivity
import com.example.wishlist_android.R
import com.example.wishlist_android.components.BackScaffold
import com.example.wishlist_android.components.WishForm
import com.example.wishlist_android.utils.handleErrors
import com.example.wishlist_android.utils.navigateAndClearHistory
import com.example.wishlist_android.wishlist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    val wishApi = MainActivity.wishApi
    var isLoading by remember { mutableStateOf(false) }

    BackScaffold(
        navController = navController,
    ) {
        WishForm(
            onSubmit = { name, price, link, currency, image ->

                CoroutineScope(Dispatchers.Main).launch {
                    withContext(Dispatchers.Main) {

                        try {
                            isLoading = true

                            val response = wishApi.createWish(
                                currency = currency,
                                name = name,
                                price = price,
                                image = image,
                                link = link,
                            )

                            if (response.isSuccessful) {
                                val result = response.body()?.result
                                if (result != null) {
                                    wishlist.add(result)
                                }
                                navigateAndClearHistory(navController, "wishlist", "addWish")
                            } else {
                                handleErrors(response, navController, "addWish")
                            }

                        } catch (e: Exception) {
                            handleErrors(e, navController, context)
                        } finally {
                            isLoading = false
                        }
                    }
                }
            },
            buttonTitle = stringResource(R.string.add_wish),
            title = stringResource(R.string.add_wish),
            isLoading = isLoading
        ) {}

    }
}