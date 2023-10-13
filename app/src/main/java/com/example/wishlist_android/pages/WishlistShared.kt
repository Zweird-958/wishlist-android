package com.example.wishlist_android.pages

import FormField
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.wishlist_android.classes.User
import com.example.wishlist_android.components.Drawer
import com.example.wishlist_android.components.LoaderRoundedButton
import com.example.wishlist_android.components.PopUpFullSize
import com.example.wishlist_android.components.UsersList
import com.example.wishlist_android.models.ShareFormModel
import com.example.wishlist_android.providers.ShareFormProvider
import com.example.wishlist_android.utils.api
import kotlinx.coroutines.launch

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
    val usersSharedWith = remember { mutableStateListOf<User>() }

    val isPopupVisible = remember { mutableStateOf(false) }
    val popupScale = remember { Animatable(0f) }
    val isLoading = remember { mutableStateOf(false) }
    val userToUnshare = remember { mutableStateOf<User?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(users, usersSharedWith) {
        val response = api(
            response = { wishApi.getSharedUsers() },
            context,
            navController,
            "wishlistShared"
        )
        val result = response.result
        if (result != null) {
            users.clear()
            if (result != null) {
                users.addAll(result)
            }
        }

        val responseSharedWith = api(
            response = { wishApi.getUsersSharedWith() },
            context,
            navController,
            "wishlistShared"
        )
        val usersSharedWithResult = responseSharedWith.result
        if (usersSharedWithResult != null) {
            usersSharedWith.clear()
            if (usersSharedWithResult != null) {
                usersSharedWith.addAll(usersSharedWithResult)
            }
        }
    }


    fun addUser() {
        scope.launch {
            val response = api(
                response = { wishApi.addSharedUser(ShareWishlistBody(username)) },
                context = context,
                navController = navController,
                currentRoute = "wishlistShared"
            )

            val result = response.result
            if (result != null) {
                Toast.makeText(context, result.message, Toast.LENGTH_SHORT)
                    .show()
                usersSharedWith.add(result.user)
            }

        }
    }

    suspend fun unshare() {
        if (userToUnshare.value == null || isLoading.value) {
            return
        }

        isLoading.value = true

        val response = api(
            response = { wishApi.unshareWish(userToUnshare.value!!.id) },
            context = context,
            navController = navController,
            currentRoute = "wishlistShared"
        )

        val result = response.result
        if (result != null) {
            usersSharedWith.remove(userToUnshare.value!!)
            userToUnshare.value = null
            Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
        }
        isLoading.value = false
        popupScale.animateTo(0f, animationSpec = tween(300))
        isPopupVisible.value = false
    }

    Drawer(
        title = stringResource(R.string.wishlist_shared),
        navController = navController,
    ) {

        if (isPopupVisible.value) {
            PopUpFullSize(
                popupScale = popupScale,
                isPopupVisible = isPopupVisible,
                isLoading = isLoading,
                textContent = stringResource(
                    R.string.unshare_user,
                    userToUnshare.value?.username ?: ""
                ),
            ) {
                scope.launch {
                    unshare()
                }
            }
        }

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

            UsersList(
                users = users,
                icon = Icons.Default.ArrowForwardIos,
                title = stringResource(R.string.wishlist_shared),
                onClick = { user -> navController.navigate("wishlist/${user.id}") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            UsersList(
                users = usersSharedWith,
                icon = Icons.Default.Close,
                title = stringResource(R.string.users_shared_with),
                onClick = { user ->
                    scope.launch {
                        userToUnshare.value = user
                        isPopupVisible.value = true
                        popupScale.animateTo(1f, animationSpec = tween(300))
                    }
                }
            )
        }
    }

}