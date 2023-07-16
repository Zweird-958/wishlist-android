package com.example.wishlist_android.models

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UserFormState(
    val email: String = "",
    val emailError: String? = null,

    val password: String = "",
    val passwordError: String? = null,
)


class UserFormModel : ViewModel() {
    private val _uiState = MutableStateFlow(UserFormState())
    val uiState: StateFlow<UserFormState> = _uiState.asStateFlow()


    fun updateEmail(email: String) {
        var error: String? = null

        if (email.isEmpty()) {
            error = "empty error"
        } else if (email.length < 3) {
            error = "len error"
        }

        _uiState.value = _uiState.value.copy(
            email = email,
            emailError = error
        )
    }

    fun updatePassword(password: String) {
        var error: String? = null

        if (password.isEmpty()) {
            error = "password required"
        } else if (password.length < 8) {
            error = "password too short"
        }

        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = error
        )
    }
}