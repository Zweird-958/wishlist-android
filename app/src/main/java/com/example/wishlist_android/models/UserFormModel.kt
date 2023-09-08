package com.example.wishlist_android.models

import androidx.lifecycle.ViewModel
import com.example.wishlist_android.providers.UserFormProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UserFormState(
    val username: String = "",
    val usernameError: String? = null,

    val email: String = "",
    val emailError: String? = null,

    val password: String = "",
    val passwordError: String? = null,
)


class UserFormModel : ViewModel() {
    private val _uiState = MutableStateFlow(UserFormState())
    val uiState: StateFlow<UserFormState> = _uiState.asStateFlow()
    var userFormProvider: UserFormProvider? = null

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
        return emailRegex.matches(email)
    }

    fun checkFormValidity(checkUsername: Boolean = false): Boolean {
        val emailValid = checkEmailValidity(_uiState.value.email) == null
        val passwordValid = checkPasswordValidity(_uiState.value.password) == null

        if (!checkUsername) {
            return emailValid && passwordValid
        }

        val usernameValid = checkUsernameValidity(_uiState.value.username) == null

        return emailValid && passwordValid && usernameValid
    }

    private fun checkEmailValidity(email: String): String? {
        var error: String? = null

        if (email.isEmpty()) {
            error = userFormProvider?.emailRequired
        } else if (!isEmailValid(email)) {
            error = userFormProvider?.emailInvalid
        }

        return error
    }

    fun updateEmail(email: String): Boolean {
        val error = checkEmailValidity(email)

        _uiState.value = _uiState.value.copy(
            email = email,
            emailError = error
        )

        return error == null
    }

    private fun checkPasswordValidity(password: String): String? {
        var error: String? = null

        if (password.isEmpty()) {
            error = userFormProvider?.passwordRequired
        } else if (password.length < 8) {
            error = userFormProvider?.passwordTooShort
        }

        return error
    }

    fun updatePassword(password: String): Boolean {
        val error = checkPasswordValidity(password)

        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = error
        )

        return error == null
    }

    private fun checkUsernameValidity(username: String): String? {
        var error: String? = null

        if (username.isEmpty()) {
            error = userFormProvider?.usernameRequired
        } else if (username.length < 3) {
            error = userFormProvider?.usernameTooShort
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