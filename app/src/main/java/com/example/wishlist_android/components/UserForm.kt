package com.example.wishlist_android.components

import Form
import FormField
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wishlist_android.R
import com.example.wishlist_android.models.UserFormModel
import com.example.wishlist_android.providers.UserFormProvider
import kotlinx.coroutines.DelicateCoroutinesApi

@OptIn(ExperimentalComposeUiApi::class, DelicateCoroutinesApi::class)
@Composable
fun UserForm(
    onSubmit: (String, String) -> Unit,
    buttonTitle: String,
    title: String,
    userFormModel: UserFormModel = viewModel(),
    isLoading: Boolean,
    children: @Composable () -> Unit,
) {
    userFormModel.userFormProvider = UserFormProvider(context = LocalContext.current)

    val formUiState by userFormModel.uiState.collectAsState()
    val email = formUiState.email
    val password = formUiState.password

    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


    fun handleSubmit() {
        focusManager.clearFocus()

        if (!userFormModel.checkFormValidity()) {
            return@handleSubmit
        }

        onSubmit(email, password)

    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                }
            }
    ) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Form(
                title = title,
                buttonTitle = buttonTitle,
                buttonEnabled = userFormModel.checkFormValidity() && !isLoading,
                onSubmit = {
                    handleSubmit()
                },
                isLoading = isLoading,
            ) {
                FormField(
                    modifier = Modifier.focusRequester(emailFocusRequester),
                    label = stringResource(R.string.email),
                    initialValue = email,
                    onValueChange = { userFormModel.updateEmail(it) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { passwordFocusRequester.requestFocus() },
                    ),
                    error = formUiState.emailError,
                )
                PasswordTextField(
                    modifier = Modifier.focusRequester(passwordFocusRequester),
                    initialValue = password,
                    onValueChange = { userFormModel.updatePassword(it) },
                    label = stringResource(R.string.password),
                    error = formUiState.passwordError,
                    keyboardActions = KeyboardActions(
                        onDone = { handleSubmit() },
                    ),
                )
                children()
            }


        }
    }

}