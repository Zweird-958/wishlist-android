package com.example.wishlist_android.pages

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.wishlist_android.R
import com.example.wishlist_android.api.RetrofitHelper
import com.example.wishlist_android.api.WishApi
import com.example.wishlist_android.components.CustomScaffold
import com.example.wishlist_android.components.WishForm
import com.example.wishlist_android.utils.formatImageBody
import com.example.wishlist_android.utils.formatStringRequestBody
import com.example.wishlist_android.utils.handleErrors
import com.example.wishlist_android.utils.navigateAndClearHistory
import com.example.wishlist_android.utils.replaceCommaWithDot
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
    val wishApi = RetrofitHelper.getInstance().create(WishApi::class.java)
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }

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
        WishForm(
            onSubmit = { name, price, link, currency, image ->

                CoroutineScope(Dispatchers.Main).launch {
                    withContext(Dispatchers.Main) {

                        try {
                            isLoading = true

                            val response = wishApi.createWish(
                                currency = formatStringRequestBody(currency),
                                name = formatStringRequestBody(name),
                                price = formatStringRequestBody(replaceCommaWithDot(price)),
                                image = formatImageBody(image),
                                link = link?.let { formatStringRequestBody(it) },
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
                            handleErrors(e, navController, "signIn", context)
                        } finally {
                            isLoading = false
                        }
                    }
                }
            },
            buttonTitle = stringResource(R.string.add_wish),
            title = stringResource(R.string.add_wish),
            isLoading = isLoading
        ) {

        }

    }
}