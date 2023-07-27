package com.example.wishlist_android.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.wishlist_android.R
import com.example.wishlist_android.api.classes.Wish
import com.example.wishlist_android.utils.dpToPx
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun WishSwipeableCard(wish: Wish, onClick: () -> Unit = { }) {
    val startOffsetX = 0f
    val offsetX = remember { Animatable(startOffsetX) }
    val limitDp = 100f
    val limitPadding = 20.dp
    val limit = dpToPx(context = LocalContext.current, dpValue = limitDp)
    val scope = rememberCoroutineScope()


    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(IntrinsicSize.Max)
            .pointerInput(Unit) {
                coroutineScope {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, x ->
                            val newOffsetX = offsetX.value + x
                            if (newOffsetX > -limit && newOffsetX < 0f) {
                                launch {
                                    offsetX.snapTo(newOffsetX)
                                }
                            }
                        },
                        onDragEnd = {
                            if (offsetX.value < -limit / 2) {
                                launch {
                                    offsetX.animateTo(-limit)
                                }
                            } else {
                                launch {
                                    offsetX.animateTo(startOffsetX)
                                }
                            }
                        },
                    )
                }
            }

    ) {
        WishCard(
            modifier = Modifier.offset { IntOffset(offsetX.value.roundToInt(), 0) },
            wish = wish
        )
        Card(
            modifier = Modifier
                .width(limitDp.dp + limitPadding)
                .zIndex(-1f)
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                ) {
                    scope.launch {
                        offsetX.animateTo(startOffsetX, animationSpec = tween(500))
                    }
                    onClick()
                },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
            ),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            )
            {
                Icon(
                    modifier = Modifier.padding(start = limitPadding),
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete),
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )

            }
        }
    }
}