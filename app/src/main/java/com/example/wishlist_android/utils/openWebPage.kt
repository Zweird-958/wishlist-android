package com.example.wishlist_android.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openWebPage(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}