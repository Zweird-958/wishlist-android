package com.example.wishlist_android.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    label: String,
    initialValue: String,
    onValueChange: (String) -> Unit,
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = initialValue,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val icon =
                if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            IconButton(
                onClick = { passwordVisibility = !passwordVisibility },
                content = { Icon(icon, contentDescription = "Toggle password visibility") }
            )
        },
    )
}