package com.example.wishlist_android.pages

import FormField
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wishlist_android.MainActivity.Companion.wishApi
import com.example.wishlist_android.R
import com.example.wishlist_android.api.classes.ShareWishlistBody
import com.example.wishlist_android.api.classes.User
import com.example.wishlist_android.components.Drawer
import com.example.wishlist_android.components.LoaderRoundedButton
import com.example.wishlist_android.models.ShareFormModel
import com.example.wishlist_android.providers.ShareFormProvider
import com.example.wishlist_android.utils.handleErrors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WishlistShared(navController: NavController) {
    val shareFormModel = viewModel(ShareFormModel::class.java)
    val context = LocalContext.current

    shareFormModel.shareFormProvider = ShareFormProvider(context)

    val formUiState by shareFormModel.uiState.collectAsState()
    val username = formUiState.username
    val users = remember { mutableStateListOf<User>() }

    LaunchedEffect(users) {
        val response = wishApi.getSharedUsers()
        if (response.isSuccessful) {
            users.clear()
            val usersResult = response.body()?.result
            if (usersResult != null) {
                users.addAll(usersResult)
            }
        } else {
            handleErrors(response, navController, "wishlistShared")
        }
    }

    fun addUser() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main) {
                val response = wishApi.addSharedUser(ShareWishlistBody(username))
                if (response.isSuccessful) {
                    Toast.makeText(context, response.body()?.result, Toast.LENGTH_SHORT).show()
                } else {
                    handleErrors(response, navController, "wishlistShared")
                }
            }
        }
    }

    Drawer(
        title = stringResource(R.string.wishlist_shared),
        navController = navController,
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
        ) {
            FormField(
                label = stringResource(R.string.usernametoshare),
                initialValue = username,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        addUser()
                    }
                ),
                onValueChange = {
                    shareFormModel.updateUsername(it)
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            LoaderRoundedButton(
                onSubmit = { addUser() },
                loading = false,
                isEnabled = shareFormModel.checkFormValidity()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = stringResource(R.string.add_user),
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = stringResource(R.string.wishlist_shared))
            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn {


                items(users) { user ->

                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = { navController.navigate("wishlist/${user.id}") }) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(text = user.username)
                            Icon(
                                Icons.Default.ArrowForwardIos,
                                contentDescription = stringResource(R.string.back_button)
                            )
                        }
                    }
                    Divider(thickness = 1.dp)
                }
            }
        }
    }

}