package com.example.wishlist_android.components

import Form
import FormField
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.wishlist_android.api.RetrofitHelper
import com.example.wishlist_android.api.WishApi
import com.example.wishlist_android.api.classes.LoginRequest
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun UserForm(onSubmit: () -> Unit, buttonTitle: String, title: String) {
    val contextForToast = LocalContext.current.applicationContext
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Form(
        title = title,
        buttonTitle = buttonTitle,
        onSubmit = {
            val quotesApi = RetrofitHelper.getInstance().create(WishApi::class.java)
            // launching a new coroutine
            GlobalScope.launch {
                val result = quotesApi.getQuotes(
                    LoginRequest(
                        email = email,
                        password = password
                    )
                )

                if (result.isSuccessful) {
                    Log.d("ayush: ", result.toString() + result.body().toString())
                } else {
                    Log.d("ayush: ", result.toString() + result.errorBody()?.string())
                }

            }
        }
    ) {
        FormField(
            label = "Email",
            initialValue = email,
            onValueChange = { email = it }
        )
        FormField(
            label = "Mot de passe",
            initialValue = password,
            onValueChange = { password = it }
        )
    }
}