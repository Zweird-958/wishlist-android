package com.example.wishlist_android.providers

import android.content.Context
import com.example.wishlist_android.R

class WishFormProvider(context: Context) {
    val nameRequired = context.getString(R.string.name_required)
    val linkInvalid = context.getString(R.string.invalid_link)
    val priceInvalid = context.getString(R.string.invalid_price)
}