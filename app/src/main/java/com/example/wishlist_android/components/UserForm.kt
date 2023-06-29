package com.example.wishlist_android.components

import Form
import FormField
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.DelicateCoroutinesApi

@SuppressLint("ShowToast")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun UserForm(
    onSubmit: (String, String) -> Unit,
    buttonTitle: String,
    title: String,
    children: @Composable () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        children()
    }


}