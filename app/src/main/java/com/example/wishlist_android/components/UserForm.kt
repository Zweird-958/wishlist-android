package com.example.wishlist_android.components

import Form
import FormField
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.DelicateCoroutinesApi

@SuppressLint("ShowToast")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun UserForm(onSubmit: (String, String) -> Unit, buttonTitle: String, title: String) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
        PasswordTextField(
            initialValue = password,
            onValueChange = { password = it },
            label = "Mot de passe"
        )
    }
}