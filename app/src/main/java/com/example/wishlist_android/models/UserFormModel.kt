package com.example.wishlist_android.models

import androidx.lifecycle.ViewModel
import com.example.wishlist_android.providers.UserFormProvider
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
    var userFormProvider: UserFormProvider? = null

    val isFormValid: Boolean
        get() = _uiState.value.emailError == null && _uiState.value.passwordError == null

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
        return emailRegex.matches(email)
    }

    fun checkFormValidity(): Boolean {
        val emailValid = updateEmail(_uiState.value.email)
        val passwordValid = updatePassword(_uiState.value.password)

        return emailValid && passwordValid
    }

    fun updateEmail(email: String): Boolean {
        var error: String? = null

        if (email.isEmpty()) {
            error = userFormProvider?.emailRequired
        } else if (!isEmailValid(email)) {
            error = userFormProvider?.emailInvalid
        }

        _uiState.value = _uiState.value.copy(
            email = email,
            emailError = error
        )

        return error == null
    }

    fun updatePassword(password: String): Boolean {
        var error: String? = null

        if (password.isEmpty()) {
            error = userFormProvider?.passwordRequired
        } else if (password.length < 8) {
            error = userFormProvider?.passwordTooShort
        }
        
        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = error
        )

        return error == null
    }
}