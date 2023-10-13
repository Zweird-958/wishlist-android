package com.example.wishlist_android.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TextButton
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wishlist_android.R
import com.example.wishlist_android.api.classes.User

@Composable
fun UsersList(users: List<User>, icon: ImageVector, title: String, onClick: (User) -> Unit) {
    Text(text = title)
    Spacer(modifier = Modifier.height(20.dp))
    LazyColumn {


        items(users) { user ->

            TextButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { onClick(user) }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(text = user.username)
                    Icon(
                        icon,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
            Divider(thickness = 1.dp)
        }
    }
}