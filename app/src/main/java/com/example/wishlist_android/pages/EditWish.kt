package com.example.wishlist_android.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.wishlist_android.MainActivity.Companion.wishApi
import com.example.wishlist_android.MainActivity.Companion.wishlist
import com.example.wishlist_android.R
import com.example.wishlist_android.classes.Wish
import com.example.wishlist_android.components.BackScaffold
import com.example.wishlist_android.components.WishForm
import com.example.wishlist_android.utils.api
import com.example.wishlist_android.utils.formatStringRequestBody
import com.example.wishlist_android.utils.navigateAndClearHistory
import kotlinx.coroutines.launch

@Composable
fun EditWish(navController: NavController, id: Int?) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var isLoading by remember { mutableStateOf(false) }
    val wish = wishlist.find { it.id == id }
    var purchased by remember { mutableStateOf(wish?.purchased ?: false) }
    var isPrivate by remember { mutableStateOf(wish?.isPrivate ?: false) }

    LaunchedEffect(id) {
        if (id == null) {
            navigateAndClearHistory(navController, "wishlist", "editWish")
        }
    }

    BackScaffold(navController = navController) {
        WishForm(
            onSubmit = { name, price, link, currency, image ->
                scope.launch {
                    isLoading = true

                    val response = api(
                        response = {
                            wishApi.editWish(
                                id = id!!,
                                currency = currency,
                                name = name,
                                price = price,
                                image = image,
                                link = link,
                                purchased = formatStringRequestBody(purchased.toString()),
                                isPrivate = formatStringRequestBody(isPrivate.toString())
                            )
                        },
                        context = context,
                        navController = navController,
                        currentRoute = "editWish"
                    )


                    val result = response.result
                    if (result != null) {
                        wishlist = wishlist.map { wish ->
                            if (wish.id == id) {
                                result
                            } else {
                                wish
                            }
                        } as MutableList<Wish>
                    }

                    navigateAndClearHistory(navController, "wishlist", "editWish")

                    isLoading = false
                }
            },
            buttonTitle = stringResource(R.string.update),
            title = stringResource(R.string.edit_wish),
            isLoading = isLoading,
            wish = wish
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.bought))
                Switch(checked = purchased, onCheckedChange = { purchased = it })
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.private_field))
                Switch(checked = isPrivate, onCheckedChange = { isPrivate = it })
            }
        }
    }

}