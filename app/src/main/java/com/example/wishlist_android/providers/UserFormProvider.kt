package com.example.wishlist_android.providers

import android.content.Context
import com.example.wishlist_android.R

class UserFormProvider(context: Context) {
    val emailRequired = context.getString(R.string.email_required)
    val emailInvalid = context.getString(R.string.invalid_email)

    val passwordRequired = context.getString(R.string.password_required)
    val passwordTooShort = context.getString(R.string.password_too_short)


}