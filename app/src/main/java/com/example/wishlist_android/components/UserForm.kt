package com.example.wishlist_android.components

import Form
import FormField
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.DelicateCoroutinesApi

@SuppressLint("ShowToast")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun UserForm(onSubmit: (String, String) -> Unit, buttonTitle: String, title: String) {
//    val contextForToast = LocalContext.current.applicationContext
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Form(
        title = title,
        buttonTitle = buttonTitle,
        onSubmit = {
            onSubmit(email, password)
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