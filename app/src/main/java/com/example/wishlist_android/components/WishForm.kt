package com.example.wishlist_android.components

import Form
import FormField
import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wishlist_android.R
import com.example.wishlist_android.currencies
import com.example.wishlist_android.models.WishFormModel
import com.example.wishlist_android.pages.getFileFromUri
import com.example.wishlist_android.providers.WishFormProvider
import java.io.File

@SuppressLint("RememberReturnType", "Range")
@Composable
fun WishForm(
    onSubmit: (String, String, String?, String, File?) -> Unit,
    buttonTitle: String,
    title: String,
    wishFormModel: WishFormModel = viewModel(),
    isLoading: Boolean,
    children: @Composable () -> Unit,
) {
    wishFormModel.wishFormProvider = WishFormProvider(context = LocalContext.current)

    val formUiState by wishFormModel.uiState.collectAsState()

    val name = formUiState.name
    val link = formUiState.link
    val price = formUiState.price

    var selectedCurrency by remember { mutableStateOf(if (currencies.isNotEmpty()) currencies[0] else "") }

    val context = LocalContext.current

    val focusManager = LocalFocusManager.current
    val focusRequesters = listOf("name", "link", "price").associateWith {
        remember { FocusRequester() }
    }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var isMenuExpanded by remember { mutableStateOf(false) }

    var photoUri: Uri? by remember { mutableStateOf(null) }
    var image: File? by remember { mutableStateOf(null) }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri == null) {
                return@rememberLauncherForActivityResult
            }

            photoUri = uri
            image = getFileFromUri(uri, context)

        }


    fun handleSubmit() {
        focusManager.clearFocus()

        if (!wishFormModel.checkFormValidity() || isLoading) {
            return
        }

        onSubmit(name, price, link, selectedCurrency, image)

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
                buttonEnabled = wishFormModel.checkFormValidity() && !isLoading,
                onSubmit = {
                    handleSubmit()
                },
                isLoading = isLoading,
            ) {
                FormField(
                    modifier = Modifier.focusRequester(focusRequesters["name"]!!),
                    label = stringResource(R.string.name),
                    initialValue = name,
                    onValueChange = { wishFormModel.updateName(it) },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequesters["price"]!!.requestFocus() },
                    ),
                    error = formUiState.nameError,
                )
                FormField(
                    modifier = Modifier.focusRequester(focusRequesters["price"]!!),
                    label = stringResource(R.string.price),
                    initialValue = price,
                    onValueChange = { wishFormModel.updatePrice(it) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequesters["link"]!!.requestFocus() },
                    ),
                    error = formUiState.priceError,
                )
                FormField(
                    modifier = Modifier.focusRequester(focusRequesters["link"]!!),
                    label = stringResource(R.string.link),
                    initialValue = link ?: "",
                    onValueChange = { wishFormModel.updateLink(it) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.clearFocus() },
                    ),
                    error = formUiState.linkError,
                )



                Box(modifier = Modifier.padding(vertical = 16.dp)) {

                    RoundedButton(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                        ),
                        onSubmit = { isMenuExpanded = !isMenuExpanded }) {
                        Text(
                            text = selectedCurrency,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }


                    DropdownMenu(
                        modifier = Modifier
                            .width(screenWidth - 64.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                            ),
                        expanded = isMenuExpanded,
                        onDismissRequest = { isMenuExpanded = false },
                    ) {

                        for (currency in currencies) {
                            DropdownMenuItem(
                                onClick = {
                                    selectedCurrency = currency
                                    isMenuExpanded = false
                                }
                            ) {
                                Text(
                                    text = currency,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    if (photoUri != null) {
                        WishImage(image = photoUri!!)
                    }



                    RoundedButton(
                        onSubmit = {
                            launcher.launch(
                                PickVisualMediaRequest(
                                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                    ) {
                        Text(
                            if (photoUri == null) stringResource(R.string.add_image) else stringResource(
                                R.string.change_image
                            ),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                children()
            }


        }
    }

}