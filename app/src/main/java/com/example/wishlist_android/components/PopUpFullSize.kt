package com.example.wishlist_android.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.wishlist_android.R
import kotlinx.coroutines.launch

@Composable
fun PopUpFullSize(
    popupScale: Animatable<Float, AnimationVector1D>,
    isPopupVisible: MutableState<Boolean>,
    isLoading: MutableState<Boolean>,
    textContent: String,
    onConfirm: () -> Unit,
) {
    val scope = rememberCoroutineScope()


    fun closePopup() {
        scope.launch {
            popupScale.animateTo(0f)
            isPopupVisible.value = false
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Popup(
            onDismissRequest = {
                closePopup()
            },
            properties = PopupProperties(
                focusable = true,
                dismissOnBackPress = true,
            ),
            alignment = Alignment.Center,
        ) {

            Card(
                modifier = Modifier
                    .scale(popupScale.value)
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = textContent,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center,
                    )

                    Row {
                        RoundedButton(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp),
                            onSubmit = {
                                closePopup()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.cancel),
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                        LoaderRoundedButton(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp),
                            loading = isLoading.value,
                            isEnabled = !isLoading.value,
                            onSubmit = {
                                onConfirm()
                            }

                        ) {
                            Text(
                                text = stringResource(R.string.confirm),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }

                }
            }

        }
    }
}