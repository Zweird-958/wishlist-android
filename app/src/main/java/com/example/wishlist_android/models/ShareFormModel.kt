package com.example.wishlist_android.models

import androidx.lifecycle.ViewModel
import com.example.wishlist_android.providers.ShareFormProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ShareFormState(
    val username: String = "",
    val usernameError: String? = null,

    )


class ShareFormModel : ViewModel() {
    private val _uiState = MutableStateFlow(ShareFormState())
    val uiState: StateFlow<ShareFormState> = _uiState.asStateFlow()
    var shareFormProvider: ShareFormProvider? = null


    fun checkFormValidity(checkUsername: Boolean = false): Boolean {

        return checkUsernameValidity(_uiState.value.username) == null
    }

    private fun checkUsernameValidity(username: String): String? {
        var error: String? = null

        if (username.isEmpty()) {
            error = shareFormProvider?.usernameRequired
        }

        return error
    }

    fun updateUsername(username: String): Boolean {
        val error = checkUsernameValidity(username)

        _uiState.value = _uiState.value.copy(
            username = username,
            usernameError = error
        )

        return error == null
    }
}