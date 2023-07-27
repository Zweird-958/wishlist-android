package com.example.wishlist_android.utils

import android.content.Context

fun dpToPx(context: Context, dpValue: Float): Float {
    return dpValue * context.resources.displayMetrics.density
}