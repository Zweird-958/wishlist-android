package com.example.wishlist_android.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Dropdown(
    modifier: Modifier = Modifier,
    buttonModifier: Modifier? = null,
    selectedChoice: MutableState<String>? = null,
    choices: List<String>,
    onClick: (String) -> Unit = {},
    width: Int,
    textLabel: String? = null,
    choicesLabel: (String) -> String = { it },
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        RoundedButton(
            modifier = buttonModifier ?: Modifier.width(width.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            onSubmit = { menuExpanded = !menuExpanded }) {
            (textLabel ?: selectedChoice?.value)?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }


        DropdownMenu(
            modifier = Modifier
                .width(width.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                ),
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false },
        ) {

            for (choice in choices) {
                DropdownMenuItem(
                    onClick = {
                        selectedChoice?.value = choice
                        menuExpanded = false
                        onClick(choice)
                    }
                ) {
                    Text(
                        text = choicesLabel(choice),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}