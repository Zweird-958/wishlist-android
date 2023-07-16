package com.example.wishlist_android.components

import Form
import FormField
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.wishlist_android.R

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
                label = stringResource(R.string.email),
                initialValue = email,
                onValueChange = { email = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false
                )
            )
            PasswordTextField(
                initialValue = password,
                onValueChange = { password = it },
                label = stringResource(R.string.password)
            )
        }
        children()
    }


}